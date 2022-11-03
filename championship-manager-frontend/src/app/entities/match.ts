import { Championship } from "./championship";
import { Team } from "./team";

export interface Match{
    id: number;
    championship: Championship;
    away_team: Team;
    home_team: Team;
    date: string;
    time: string;
    place: string;
    scoreboard: string;
    status: string;
    type: string;
    events: Event[];
}