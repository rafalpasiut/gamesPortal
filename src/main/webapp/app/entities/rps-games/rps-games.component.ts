import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RPSGames } from './rps-games.model';
import { RPSGamesService } from './rps-games.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-rps-games',
    templateUrl: './rps-games.component.html'
})
export class RPSGamesComponent implements OnInit, OnDestroy {
rPSGames: RPSGames[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private rPSGamesService: RPSGamesService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.rPSGamesService.query().subscribe(
            (res: ResponseWrapper) => {
                this.rPSGames = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInRPSGames();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: RPSGames) {
        return item.id;
    }
    registerChangeInRPSGames() {
        this.eventSubscriber = this.eventManager.subscribe('rPSGamesListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
