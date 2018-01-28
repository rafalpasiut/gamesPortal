import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GamesPortalSharedModule } from '../../shared';
import {
    SudokuCellEntityService,
    SudokuCellEntityPopupService,
    SudokuCellEntityComponent,
    SudokuCellEntityDetailComponent,
    SudokuCellEntityDialogComponent,
    SudokuCellEntityPopupComponent,
    SudokuCellEntityDeletePopupComponent,
    SudokuCellEntityDeleteDialogComponent,
    sudokuCellEntityRoute,
    sudokuCellEntityPopupRoute,
} from './';

const ENTITY_STATES = [
    ...sudokuCellEntityRoute,
    ...sudokuCellEntityPopupRoute,
];

@NgModule({
    imports: [
        GamesPortalSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SudokuCellEntityComponent,
        SudokuCellEntityDetailComponent,
        SudokuCellEntityDialogComponent,
        SudokuCellEntityDeleteDialogComponent,
        SudokuCellEntityPopupComponent,
        SudokuCellEntityDeletePopupComponent,
    ],
    entryComponents: [
        SudokuCellEntityComponent,
        SudokuCellEntityDialogComponent,
        SudokuCellEntityPopupComponent,
        SudokuCellEntityDeleteDialogComponent,
        SudokuCellEntityDeletePopupComponent,
    ],
    providers: [
        SudokuCellEntityService,
        SudokuCellEntityPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GamesPortalSudokuCellEntityModule {}
