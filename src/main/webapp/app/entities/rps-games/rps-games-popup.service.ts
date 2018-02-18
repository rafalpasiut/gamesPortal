import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { RPSGames } from './rps-games.model';
import { RPSGamesService } from './rps-games.service';

@Injectable()
export class RPSGamesPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private rPSGamesService: RPSGamesService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.rPSGamesService.find(id).subscribe((rPSGames) => {
                    rPSGames.gameStartTime = this.datePipe
                        .transform(rPSGames.gameStartTime, 'yyyy-MM-ddTHH:mm:ss');
                    rPSGames.lastActionTime = this.datePipe
                        .transform(rPSGames.lastActionTime, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.rPSGamesModalRef(component, rPSGames);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.rPSGamesModalRef(component, new RPSGames());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    rPSGamesModalRef(component: Component, rPSGames: RPSGames): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.rPSGames = rPSGames;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
