import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SudokuCellEntity } from './sudoku-cell-entity.model';
import { SudokuCellEntityPopupService } from './sudoku-cell-entity-popup.service';
import { SudokuCellEntityService } from './sudoku-cell-entity.service';

@Component({
    selector: 'jhi-sudoku-cell-entity-delete-dialog',
    templateUrl: './sudoku-cell-entity-delete-dialog.component.html'
})
export class SudokuCellEntityDeleteDialogComponent {

    sudokuCellEntity: SudokuCellEntity;

    constructor(
        private sudokuCellEntityService: SudokuCellEntityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sudokuCellEntityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'sudokuCellEntityListModification',
                content: 'Deleted an sudokuCellEntity'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sudoku-cell-entity-delete-popup',
    template: ''
})
export class SudokuCellEntityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sudokuCellEntityPopupService: SudokuCellEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.sudokuCellEntityPopupService
                .open(SudokuCellEntityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
