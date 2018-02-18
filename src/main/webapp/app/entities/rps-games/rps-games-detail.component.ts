import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { RPSGames } from './rps-games.model';
import { RPSGamesService } from './rps-games.service';

@Component({
    selector: 'jhi-rps-games-detail',
    templateUrl: './rps-games-detail.component.html'
})
export class RPSGamesDetailComponent implements OnInit, OnDestroy {

    rPSGames: RPSGames;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private rPSGamesService: RPSGamesService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRPSGames();
    }

    load(id) {
        this.rPSGamesService.find(id).subscribe((rPSGames) => {
            this.rPSGames = rPSGames;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRPSGames() {
        this.eventSubscriber = this.eventManager.subscribe(
            'rPSGamesListModification',
            (response) => this.load(this.rPSGames.id)
        );
    }
}
