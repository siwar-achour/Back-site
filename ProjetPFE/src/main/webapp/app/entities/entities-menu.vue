<template>
  <div>
    <b-dropdown-item to="/chauffeur">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.chauffeur')">Chauffeur</span>
    </b-dropdown-item>
    <b-dropdown-item to="/client">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.client')">Client</span>
    </b-dropdown-item>
    <b-dropdown-item to="/admin">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.admin')">Admin</span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
