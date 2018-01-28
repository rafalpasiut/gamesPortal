import {Injectable} from '@angular/core';
import {SudokuGenResponse} from './sudoku-game.component';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {SERVER_API_URL} from '../../app.constants';
import {Principal} from '../../shared/auth/principal.service';

@Injectable()
export class SudokuGameRequestsService {

    private resourceUrl = SERVER_API_URL;
    private user: any;

    constructor(private http: HttpClient,
                private principal: Principal) {

    }

    generateNew(hardLevel: number): Observable<SudokuGenResponse> {
        console.log(SERVER_API_URL + '/sudokuNew?level=' + hardLevel);
        return this.http.get<SudokuGenResponse>(SERVER_API_URL + '/api/sudokuNew?level=' + hardLevel).map((data) => {
            return <SudokuGenResponse>data;
        });
    }

    getSavedSudoku(): Promise<Observable<SudokuGenResponse>> {
        return this.getUserId().then((user) => {
            let userId: number;
            if (user === null) {
                userId = -1;
            } else {
                userId = user.id;
            }
            return this.http.get<SudokuGenResponse>(SERVER_API_URL + '/api/sudokuGetSaved?userId=' + userId).map((data) => {
                console.log("GET SAVED");
                console.log(data);
                return data;
            });
        });
    }

    saveChangedSudoku(sudoku: SudokuGenResponse): void {
        console.log(sudoku);
        this.getUserId().then((user) => {
            console.log(user);
            if (user !== null) {
                sudoku.userId = user.id;
                this.http.put<SudokuGenResponse>(SERVER_API_URL + '/api/sudokuSaveSudoku', sudoku).subscribe((res) => {
                });
            }
        });
    }

    getUserId(): Promise<any> {
        return this.principal.identity(true);
    }

}
