import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { SudokuCellEntityComponent } from './sudoku-cell-entity.component';
import { SudokuCellEntityDetailComponent } from './sudoku-cell-entity-detail.component';
import { SudokuCellEntityPopupComponent } from './sudoku-cell-entity-dialog.component';
import { SudokuCellEntityDeletePopupComponent } from './sudoku-cell-entity-delete-dialog.component';

export const sudokuCellEntityRoute: Routes = [
    {
        path: 'sudoku-cell-entity',
        component: SudokuCellEntityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SudokuCellEntities'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'sudoku-cell-entity/:id',
        component: SudokuCellEntityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SudokuCellEntities'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sudokuCellEntityPopupRoute: Routes = [
    {
        path: 'sudoku-cell-entity-new',
        component: SudokuCellEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SudokuCellEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sudoku-cell-entity/:id/edit',
        component: SudokuCellEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SudokuCellEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sudoku-cell-entity/:id/delete',
        component: SudokuCellEntityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SudokuCellEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
