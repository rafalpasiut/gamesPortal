/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { GamesPortalTestModule } from '../../../test.module';
import { SudokuCellEntityDetailComponent } from '../../../../../../main/webapp/app/entities/sudoku-cell-entity/sudoku-cell-entity-detail.component';
import { SudokuCellEntityService } from '../../../../../../main/webapp/app/entities/sudoku-cell-entity/sudoku-cell-entity.service';
import { SudokuCellEntity } from '../../../../../../main/webapp/app/entities/sudoku-cell-entity/sudoku-cell-entity.model';

describe('Component Tests', () => {

    describe('SudokuCellEntity Management Detail Component', () => {
        let comp: SudokuCellEntityDetailComponent;
        let fixture: ComponentFixture<SudokuCellEntityDetailComponent>;
        let service: SudokuCellEntityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GamesPortalTestModule],
                declarations: [SudokuCellEntityDetailComponent],
                providers: [
                    SudokuCellEntityService
                ]
            })
            .overrideTemplate(SudokuCellEntityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SudokuCellEntityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SudokuCellEntityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new SudokuCellEntity(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.sudokuCellEntity).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
