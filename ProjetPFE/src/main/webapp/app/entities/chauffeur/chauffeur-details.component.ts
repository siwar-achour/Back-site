import { Component, Vue, Inject } from 'vue-property-decorator';

import { IChauffeur } from '@/shared/model/chauffeur.model';
import ChauffeurService from './chauffeur.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class ChauffeurDetails extends Vue {
  @Inject('chauffeurService') private chauffeurService: () => ChauffeurService;
  @Inject('alertService') private alertService: () => AlertService;

  public chauffeur: IChauffeur = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.chauffeurId) {
        vm.retrieveChauffeur(to.params.chauffeurId);
      }
    });
  }

  public retrieveChauffeur(chauffeurId) {
    this.chauffeurService()
      .find(chauffeurId)
      .then(res => {
        this.chauffeur = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
