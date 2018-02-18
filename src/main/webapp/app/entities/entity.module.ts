import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { GamesPortalSudokuCellEntityModule } from './sudoku-cell-entity/sudoku-cell-entity.module';
import { GamesPortalRPSGamesModule } from './rps-games/rps-games.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        GamesPortalSudokuCellEntityModule,
        GamesPortalRPSGamesModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GamesPortalEntityModule {}
