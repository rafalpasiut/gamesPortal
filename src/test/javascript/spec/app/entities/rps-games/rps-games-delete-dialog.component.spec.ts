/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { GamesPortalTestModule } from '../../../test.module';
import { RPSGamesDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/rps-games/rps-games-delete-dialog.component';
import { RPSGamesService } from '../../../../../../main/webapp/app/entities/rps-games/rps-games.service';

describe('Component Tests', () => {

    describe('RPSGames Management Delete Component', () => {
        let comp: RPSGamesDeleteDialogComponent;
        let fixture: ComponentFixture<RPSGamesDeleteDialogComponent>;
        let service: RPSGamesService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GamesPortalTestModule],
                declarations: [RPSGamesDeleteDialogComponent],
                providers: [
                    RPSGamesService
                ]
            })
            .overrideTemplate(RPSGamesDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RPSGamesDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RPSGamesService);
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
