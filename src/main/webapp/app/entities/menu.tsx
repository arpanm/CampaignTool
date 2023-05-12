import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/client">
        <Translate contentKey="global.menu.entities.client" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/campaign">
        <Translate contentKey="global.menu.entities.campaign" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/lead">
        <Translate contentKey="global.menu.entities.lead" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/attribute-key">
        <Translate contentKey="global.menu.entities.attributeKey" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/attribute-possible-value">
        <Translate contentKey="global.menu.entities.attributePossibleValue" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/attribute">
        <Translate contentKey="global.menu.entities.attribute" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/location">
        <Translate contentKey="global.menu.entities.location" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/telecaller">
        <Translate contentKey="global.menu.entities.telecaller" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/telecaller-in-out">
        <Translate contentKey="global.menu.entities.telecallerInOut" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/telecaller-assignment">
        <Translate contentKey="global.menu.entities.telecallerAssignment" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/lead-association">
        <Translate contentKey="global.menu.entities.leadAssociation" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/lead-assignment">
        <Translate contentKey="global.menu.entities.leadAssignment" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/call">
        <Translate contentKey="global.menu.entities.call" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/lead-upload-file">
        <Translate contentKey="global.menu.entities.leadUploadFile" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/disposition">
        <Translate contentKey="global.menu.entities.disposition" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/field">
        <Translate contentKey="global.menu.entities.field" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/field-possible-value">
        <Translate contentKey="global.menu.entities.fieldPossibleValue" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/disposition-submission">
        <Translate contentKey="global.menu.entities.dispositionSubmission" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/disposition-submission-value">
        <Translate contentKey="global.menu.entities.dispositionSubmissionValue" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/test-result">
        <Translate contentKey="global.menu.entities.testResult" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/build">
        <Translate contentKey="global.menu.entities.build" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
