import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { SudokuCellEntity } from './sudoku-cell-entity.model';
import { SudokuCellEntityService } from './sudoku-cell-entity.service';

@Component({
    selector: 'jhi-sudoku-cell-entity-detail',
    templateUrl: './sudoku-cell-entity-detail.component.html'
})
export class SudokuCellEntityDetailComponent implements OnInit, OnDestroy {

    sudokuCellEntity: SudokuCellEntity;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private sudokuCellEntityService: SudokuCellEntityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSudokuCellEntities();
    }

    load(id) {
        this.sudokuCellEntityService.find(id).subscribe((sudokuCellEntity) => {
            this.sudokuCellEntity = sudokuCellEntity;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSudokuCellEntities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'sudokuCellEntityListModification',
            (response) => this.load(this.sudokuCellEntity.id)
        );
    }
}
