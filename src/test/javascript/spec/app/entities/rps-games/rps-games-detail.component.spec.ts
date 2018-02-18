/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { GamesPortalTestModule } from '../../../test.module';
import { RPSGamesDetailComponent } from '../../../../../../main/webapp/app/entities/rps-games/rps-games-detail.component';
import { RPSGamesService } from '../../../../../../main/webapp/app/entities/rps-games/rps-games.service';
import { RPSGames } from '../../../../../../main/webapp/app/entities/rps-games/rps-games.model';

describe('Component Tests', () => {

    describe('RPSGames Management Detail Component', () => {
        let comp: RPSGamesDetailComponent;
        let fixture: ComponentFixture<RPSGamesDetailComponent>;
        let service: RPSGamesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GamesPortalTestModule],
                declarations: [RPSGamesDetailComponent],
                providers: [
                    RPSGamesService
                ]
            })
            .overrideTemplate(RPSGamesDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RPSGamesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RPSGamesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new RPSGames(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.rPSGames).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
