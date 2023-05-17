/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ChauffeurDetailComponent from '@/entities/chauffeur/chauffeur-details.vue';
import ChauffeurClass from '@/entities/chauffeur/chauffeur-details.component';
import ChauffeurService from '@/entities/chauffeur/chauffeur.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Chauffeur Management Detail Component', () => {
    let wrapper: Wrapper<ChauffeurClass>;
    let comp: ChauffeurClass;
    let chauffeurServiceStub: SinonStubbedInstance<ChauffeurService>;

    beforeEach(() => {
      chauffeurServiceStub = sinon.createStubInstance<ChauffeurService>(ChauffeurService);

      wrapper = shallowMount<ChauffeurClass>(ChauffeurDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { chauffeurService: () => chauffeurServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundChauffeur = { id: 'ABC' };
        chauffeurServiceStub.find.resolves(foundChauffeur);

        // WHEN
        comp.retrieveChauffeur('ABC');
        await comp.$nextTick();

        // THEN
        expect(comp.chauffeur).toBe(foundChauffeur);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundChauffeur = { id: 'ABC' };
        chauffeurServiceStub.find.resolves(foundChauffeur);

        // WHEN
        comp.beforeRouteEnter({ params: { chauffeurId: 'ABC' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.chauffeur).toBe(foundChauffeur);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
