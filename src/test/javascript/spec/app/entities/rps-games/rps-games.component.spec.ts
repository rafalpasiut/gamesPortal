/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { GamesPortalTestModule } from '../../../test.module';
import { RPSGamesComponent } from '../../../../../../main/webapp/app/entities/rps-games/rps-games.component';
import { RPSGamesService } from '../../../../../../main/webapp/app/entities/rps-games/rps-games.service';
import { RPSGames } from '../../../../../../main/webapp/app/entities/rps-games/rps-games.model';

describe('Component Tests', () => {

    describe('RPSGames Management Component', () => {
        let comp: RPSGamesComponent;
        let fixture: ComponentFixture<RPSGamesComponent>;
        let service: RPSGamesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GamesPortalTestModule],
                declarations: [RPSGamesComponent],
                providers: [
                    RPSGamesService
                ]
            })
            .overrideTemplate(RPSGamesComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RPSGamesComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RPSGamesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new RPSGames(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.rPSGames[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
