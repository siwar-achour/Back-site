/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import ChauffeurComponent from '@/entities/chauffeur/chauffeur.vue';
import ChauffeurClass from '@/entities/chauffeur/chauffeur.component';
import ChauffeurService from '@/entities/chauffeur/chauffeur.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('Chauffeur Management Component', () => {
    let wrapper: Wrapper<ChauffeurClass>;
    let comp: ChauffeurClass;
    let chauffeurServiceStub: SinonStubbedInstance<ChauffeurService>;

    beforeEach(() => {
      chauffeurServiceStub = sinon.createStubInstance<ChauffeurService>(ChauffeurService);
      chauffeurServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ChauffeurClass>(ChauffeurComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          chauffeurService: () => chauffeurServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      chauffeurServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

      // WHEN
      comp.retrieveAllChauffeurs();
      await comp.$nextTick();

      // THEN
      expect(chauffeurServiceStub.retrieve.called).toBeTruthy();
      expect(comp.chauffeurs[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      chauffeurServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 'ABC' });
      expect(chauffeurServiceStub.retrieve.callCount).toEqual(1);

      comp.removeChauffeur();
      await comp.$nextTick();

      // THEN
      expect(chauffeurServiceStub.delete.called).toBeTruthy();
      expect(chauffeurServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
