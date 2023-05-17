<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="chauffeur">
        <h2 class="jh-entity-heading" data-cy="chauffeurDetailsHeading">
          <span v-text="$t('projetPfeApp.chauffeur.detail.title')">Chauffeur</span> {{ chauffeur.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('projetPfeApp.chauffeur.firstName')">First Name</span>
          </dt>
          <dd>
            <span>{{ chauffeur.firstName }}</span>
          </dd>
          <dt>
            <span v-text="$t('projetPfeApp.chauffeur.lastName')">Last Name</span>
          </dt>
          <dd>
            <span>{{ chauffeur.lastName }}</span>
          </dd>
          <dt>
            <span v-text="$t('projetPfeApp.chauffeur.email')">Email</span>
          </dt>
          <dd>
            <span>{{ chauffeur.email }}</span>
          </dd>
          <dt>
            <span v-text="$t('projetPfeApp.chauffeur.phoneNumber')">Phone Number</span>
          </dt>
          <dd>
            <span>{{ chauffeur.phoneNumber }}</span>
          </dd>
          <dt>
            <span v-text="$t('projetPfeApp.chauffeur.admin')">Admin</span>
          </dt>
          <dd>
            <div v-if="chauffeur.admin">
              <router-link :to="{ name: 'AdminView', params: { adminId: chauffeur.admin.id } }">{{ chauffeur.admin.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="chauffeur.id"
          :to="{ name: 'ChauffeurEdit', params: { chauffeurId: chauffeur.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./chauffeur-details.component.ts"></script>
