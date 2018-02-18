import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RPSGames } from './rps-games.model';
import { RPSGamesPopupService } from './rps-games-popup.service';
import { RPSGamesService } from './rps-games.service';

@Component({
    selector: 'jhi-rps-games-dialog',
    templateUrl: './rps-games-dialog.component.html'
})
export class RPSGamesDialogComponent implements OnInit {

    rPSGames: RPSGames;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private rPSGamesService: RPSGamesService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.rPSGames.id !== undefined) {
            this.subscribeToSaveResponse(
                this.rPSGamesService.update(this.rPSGames));
        } else {
            this.subscribeToSaveResponse(
                this.rPSGamesService.create(this.rPSGames));
        }
    }

    private subscribeToSaveResponse(result: Observable<RPSGames>) {
        result.subscribe((res: RPSGames) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: RPSGames) {
        this.eventManager.broadcast({ name: 'rPSGamesListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-rps-games-popup',
    template: ''
})
export class RPSGamesPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private rPSGamesPopupService: RPSGamesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.rPSGamesPopupService
                    .open(RPSGamesDialogComponent as Component, params['id']);
            } else {
                this.rPSGamesPopupService
                    .open(RPSGamesDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
