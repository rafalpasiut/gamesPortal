/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { GamesPortalTestModule } from '../../../test.module';
import { RPSGamesDialogComponent } from '../../../../../../main/webapp/app/entities/rps-games/rps-games-dialog.component';
import { RPSGamesService } from '../../../../../../main/webapp/app/entities/rps-games/rps-games.service';
import { RPSGames } from '../../../../../../main/webapp/app/entities/rps-games/rps-games.model';

describe('Component Tests', () => {

    describe('RPSGames Management Dialog Component', () => {
        let comp: RPSGamesDialogComponent;
        let fixture: ComponentFixture<RPSGamesDialogComponent>;
        let service: RPSGamesService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GamesPortalTestModule],
                declarations: [RPSGamesDialogComponent],
                providers: [
                    RPSGamesService
                ]
            })
            .overrideTemplate(RPSGamesDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RPSGamesDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RPSGamesService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new RPSGames(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.rPSGames = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'rPSGamesListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new RPSGames();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.rPSGames = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'rPSGamesListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
