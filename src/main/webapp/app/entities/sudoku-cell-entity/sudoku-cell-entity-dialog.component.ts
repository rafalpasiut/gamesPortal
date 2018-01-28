import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SudokuCellEntity } from './sudoku-cell-entity.model';
import { SudokuCellEntityPopupService } from './sudoku-cell-entity-popup.service';
import { SudokuCellEntityService } from './sudoku-cell-entity.service';

@Component({
    selector: 'jhi-sudoku-cell-entity-dialog',
    templateUrl: './sudoku-cell-entity-dialog.component.html'
})
export class SudokuCellEntityDialogComponent implements OnInit {

    sudokuCellEntity: SudokuCellEntity;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private sudokuCellEntityService: SudokuCellEntityService,
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
        if (this.sudokuCellEntity.id !== undefined) {
            this.subscribeToSaveResponse(
                this.sudokuCellEntityService.update(this.sudokuCellEntity));
        } else {
            this.subscribeToSaveResponse(
                this.sudokuCellEntityService.create(this.sudokuCellEntity));
        }
    }

    private subscribeToSaveResponse(result: Observable<SudokuCellEntity>) {
        result.subscribe((res: SudokuCellEntity) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: SudokuCellEntity) {
        this.eventManager.broadcast({ name: 'sudokuCellEntityListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-sudoku-cell-entity-popup',
    template: ''
})
export class SudokuCellEntityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sudokuCellEntityPopupService: SudokuCellEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.sudokuCellEntityPopupService
                    .open(SudokuCellEntityDialogComponent as Component, params['id']);
            } else {
                this.sudokuCellEntityPopupService
                    .open(SudokuCellEntityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
