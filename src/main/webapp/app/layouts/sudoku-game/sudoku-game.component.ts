import {Component, OnInit} from '@angular/core';
import {Cell} from './sudoku-board/sudoku-board.component';
import {SudokuGameRequestsService} from './sudoku-game-requests.service';
import {JhiEventManager} from 'ng-jhipster';
import {Subject} from 'rxjs/Subject';

@Component({
    selector: 'jhi-sudoku-game',
    templateUrl: './sudoku-game.component.html',
    styleUrls: ['./sudoku-game.component.css']
})
export class SudokuGameComponent implements OnInit {

    newBoardSubject: Subject<SudokuGenResponse> = new Subject();
    loggedUserId: number;
    solutionVisible = false;
    solutionVisibleSubject: Subject<boolean> = new Subject();

    constructor(private sudokuRequest: SudokuGameRequestsService,
                private eventManager: JhiEventManager) {
    }

    ngOnInit() {
        this.registerAuthenticationSuccess();
        this.init();
    }

    generateNew(difficultLevel: number): void {
        this.sudokuRequest.generateNew(difficultLevel).subscribe(
            (data) => this.newBoardSubject.next(data)
        );
    }

    init(): void {
        this.sudokuRequest.getSavedSudoku().then((observable) => observable.subscribe(
            (data) => {
                this.newBoardSubject.next(data);
                console.log(data);
            }
        ));
    }

    showSolution(): void {

        this.solutionVisible = !this.solutionVisible;
        this.solutionVisibleSubject.next(this.solutionVisible);
        console.log('sent');
    }

    registerAuthenticationSuccess(): void {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.init();
        });
    }
}

export interface SudokuGenResponse {
    board: Cell[][];
    userId: number;
}
