/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { GamesPortalTestModule } from '../../../test.module';
import { SudokuCellEntityComponent } from '../../../../../../main/webapp/app/entities/sudoku-cell-entity/sudoku-cell-entity.component';
import { SudokuCellEntityService } from '../../../../../../main/webapp/app/entities/sudoku-cell-entity/sudoku-cell-entity.service';
import { SudokuCellEntity } from '../../../../../../main/webapp/app/entities/sudoku-cell-entity/sudoku-cell-entity.model';

describe('Component Tests', () => {

    describe('SudokuCellEntity Management Component', () => {
        let comp: SudokuCellEntityComponent;
        let fixture: ComponentFixture<SudokuCellEntityComponent>;
        let service: SudokuCellEntityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GamesPortalTestModule],
                declarations: [SudokuCellEntityComponent],
                providers: [
                    SudokuCellEntityService
                ]
            })
            .overrideTemplate(SudokuCellEntityComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SudokuCellEntityComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SudokuCellEntityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new SudokuCellEntity(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.sudokuCellEntities[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
