import { IChauffeur } from '@/shared/model/chauffeur.model';
import { IClient } from '@/shared/model/client.model';

export interface IAdmin {
  id?: string;
  roleName?: string | null;
  userName?: string | null;
  chauffeurs?: IChauffeur[] | null;
  clients?: IClient[] | null;
}

export class Admin implements IAdmin {
  constructor(
    public id?: string,
    public roleName?: string | null,
    public userName?: string | null,
    public chauffeurs?: IChauffeur[] | null,
    public clients?: IClient[] | null
  ) {}
}
