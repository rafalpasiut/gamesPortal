import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SudokuCellEntity } from './sudoku-cell-entity.model';
import { SudokuCellEntityService } from './sudoku-cell-entity.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-sudoku-cell-entity',
    templateUrl: './sudoku-cell-entity.component.html'
})
export class SudokuCellEntityComponent implements OnInit, OnDestroy {
sudokuCellEntities: SudokuCellEntity[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private sudokuCellEntityService: SudokuCellEntityService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.sudokuCellEntityService.query().subscribe(
            (res: ResponseWrapper) => {
                this.sudokuCellEntities = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSudokuCellEntities();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: SudokuCellEntity) {
        return item.id;
    }
    registerChangeInSudokuCellEntities() {
        this.eventSubscriber = this.eventManager.subscribe('sudokuCellEntityListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
