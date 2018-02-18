import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GamesPortalSharedModule } from '../../shared';
import {
    RPSGamesService,
    RPSGamesPopupService,
    RPSGamesComponent,
    RPSGamesDetailComponent,
    RPSGamesDialogComponent,
    RPSGamesPopupComponent,
    RPSGamesDeletePopupComponent,
    RPSGamesDeleteDialogComponent,
    rPSGamesRoute,
    rPSGamesPopupRoute,
} from './';

const ENTITY_STATES = [
    ...rPSGamesRoute,
    ...rPSGamesPopupRoute,
];

@NgModule({
    imports: [
        GamesPortalSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RPSGamesComponent,
        RPSGamesDetailComponent,
        RPSGamesDialogComponent,
        RPSGamesDeleteDialogComponent,
        RPSGamesPopupComponent,
        RPSGamesDeletePopupComponent,
    ],
    entryComponents: [
        RPSGamesComponent,
        RPSGamesDialogComponent,
        RPSGamesPopupComponent,
        RPSGamesDeleteDialogComponent,
        RPSGamesDeletePopupComponent,
    ],
    providers: [
        RPSGamesService,
        RPSGamesPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GamesPortalRPSGamesModule {}
