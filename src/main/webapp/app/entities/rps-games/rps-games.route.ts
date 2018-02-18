import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { RPSGamesComponent } from './rps-games.component';
import { RPSGamesDetailComponent } from './rps-games-detail.component';
import { RPSGamesPopupComponent } from './rps-games-dialog.component';
import { RPSGamesDeletePopupComponent } from './rps-games-delete-dialog.component';

export const rPSGamesRoute: Routes = [
    {
        path: 'rps-games',
        component: RPSGamesComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RPSGames'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'rps-games/:id',
        component: RPSGamesDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RPSGames'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rPSGamesPopupRoute: Routes = [
    {
        path: 'rps-games-new',
        component: RPSGamesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RPSGames'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rps-games/:id/edit',
        component: RPSGamesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RPSGames'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rps-games/:id/delete',
        component: RPSGamesDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RPSGames'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
