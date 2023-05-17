import { Component, Provide, Vue } from 'vue-property-decorator';

import UserService from '@/entities/user/user.service';
import ChauffeurService from './chauffeur/chauffeur.service';
import ClientService from './client/client.service';
import AdminService from './admin/admin.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

@Component
export default class Entities extends Vue {
  @Provide('userService') private userService = () => new UserService();
  @Provide('chauffeurService') private chauffeurService = () => new ChauffeurService();
  @Provide('clientService') private clientService = () => new ClientService();
  @Provide('adminService') private adminService = () => new AdminService();
  // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
}
