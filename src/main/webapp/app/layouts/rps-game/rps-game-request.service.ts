import {Injectable} from '@angular/core';
import {FightResult} from './rps-game.component';
import {Observable} from 'rxjs/Observable';
import {HttpClient} from '@angular/common/http';
import {SERVER_API_URL} from "../../app.constants";

@Injectable()
export class RpsGameRequestService {

    private resourceUrl = SERVER_API_URL;

    constructor(private http: HttpClient) {
    }

    public fight(champion: string): Observable<FightResult> {
        return this.http.get<FightResult>(SERVER_API_URL + 'api/rpsFightWithAI?champion=' + champion).map((data) => {
            return data;
        });
    }

}
