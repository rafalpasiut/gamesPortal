/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { GamesPortalTestModule } from '../../../test.module';
import { SudokuCellEntityDialogComponent } from '../../../../../../main/webapp/app/entities/sudoku-cell-entity/sudoku-cell-entity-dialog.component';
import { SudokuCellEntityService } from '../../../../../../main/webapp/app/entities/sudoku-cell-entity/sudoku-cell-entity.service';
import { SudokuCellEntity } from '../../../../../../main/webapp/app/entities/sudoku-cell-entity/sudoku-cell-entity.model';

describe('Component Tests', () => {

    describe('SudokuCellEntity Management Dialog Component', () => {
        let comp: SudokuCellEntityDialogComponent;
        let fixture: ComponentFixture<SudokuCellEntityDialogComponent>;
        let service: SudokuCellEntityService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GamesPortalTestModule],
                declarations: [SudokuCellEntityDialogComponent],
                providers: [
                    SudokuCellEntityService
                ]
            })
            .overrideTemplate(SudokuCellEntityDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SudokuCellEntityDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SudokuCellEntityService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SudokuCellEntity(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.sudokuCellEntity = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'sudokuCellEntityListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SudokuCellEntity();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.sudokuCellEntity = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'sudokuCellEntityListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
