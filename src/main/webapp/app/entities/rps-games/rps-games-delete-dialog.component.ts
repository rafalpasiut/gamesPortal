import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RPSGames } from './rps-games.model';
import { RPSGamesPopupService } from './rps-games-popup.service';
import { RPSGamesService } from './rps-games.service';

@Component({
    selector: 'jhi-rps-games-delete-dialog',
    templateUrl: './rps-games-delete-dialog.component.html'
})
export class RPSGamesDeleteDialogComponent {

    rPSGames: RPSGames;

    constructor(
        private rPSGamesService: RPSGamesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rPSGamesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'rPSGamesListModification',
                content: 'Deleted an rPSGames'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rps-games-delete-popup',
    template: ''
})
export class RPSGamesDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private rPSGamesPopupService: RPSGamesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.rPSGamesPopupService
                .open(RPSGamesDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
