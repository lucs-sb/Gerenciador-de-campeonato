import { Championship } from "./championship";
import { GroupInformation } from "./group-information";
import { Team } from "./team";

export interface Group{
    id: number;
    name_group: string;
    championship: Championship;
    teams: Team[];
    group_information: GroupInformation;
}