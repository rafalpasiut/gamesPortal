/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { GamesPortalTestModule } from '../../../test.module';
import { SudokuCellEntityDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/sudoku-cell-entity/sudoku-cell-entity-delete-dialog.component';
import { SudokuCellEntityService } from '../../../../../../main/webapp/app/entities/sudoku-cell-entity/sudoku-cell-entity.service';

describe('Component Tests', () => {

    describe('SudokuCellEntity Management Delete Component', () => {
        let comp: SudokuCellEntityDeleteDialogComponent;
        let fixture: ComponentFixture<SudokuCellEntityDeleteDialogComponent>;
        let service: SudokuCellEntityService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GamesPortalTestModule],
                declarations: [SudokuCellEntityDeleteDialogComponent],
                providers: [
                    SudokuCellEntityService
                ]
            })
            .overrideTemplate(SudokuCellEntityDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SudokuCellEntityDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SudokuCellEntityService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
