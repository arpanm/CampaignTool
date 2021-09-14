import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Client from './client';
import Campaign from './campaign';
import Lead from './lead';
import AttributeKey from './attribute-key';
import AttributePossibleValue from './attribute-possible-value';
import Attribute from './attribute';
import Location from './location';
import Telecaller from './telecaller';
import TelecallerInOut from './telecaller-in-out';
import TelecallerAssignment from './telecaller-assignment';
import LeadAssociation from './lead-association';
import LeadAssignment from './lead-assignment';
import Call from './call';
import LeadUploadFile from './lead-upload-file';
import Disposition from './disposition';
import Field from './field';
import FieldPossibleValue from './field-possible-value';
import DispositionSubmission from './disposition-submission';
import DispositionSubmissionValue from './disposition-submission-value';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}client`} component={Client} />
      <ErrorBoundaryRoute path={`${match.url}campaign`} component={Campaign} />
      <ErrorBoundaryRoute path={`${match.url}lead`} component={Lead} />
      <ErrorBoundaryRoute path={`${match.url}attribute-key`} component={AttributeKey} />
      <ErrorBoundaryRoute path={`${match.url}attribute-possible-value`} component={AttributePossibleValue} />
      <ErrorBoundaryRoute path={`${match.url}attribute`} component={Attribute} />
      <ErrorBoundaryRoute path={`${match.url}location`} component={Location} />
      <ErrorBoundaryRoute path={`${match.url}telecaller`} component={Telecaller} />
      <ErrorBoundaryRoute path={`${match.url}telecaller-in-out`} component={TelecallerInOut} />
      <ErrorBoundaryRoute path={`${match.url}telecaller-assignment`} component={TelecallerAssignment} />
      <ErrorBoundaryRoute path={`${match.url}lead-association`} component={LeadAssociation} />
      <ErrorBoundaryRoute path={`${match.url}lead-assignment`} component={LeadAssignment} />
      <ErrorBoundaryRoute path={`${match.url}call`} component={Call} />
      <ErrorBoundaryRoute path={`${match.url}lead-upload-file`} component={LeadUploadFile} />
      <ErrorBoundaryRoute path={`${match.url}disposition`} component={Disposition} />
      <ErrorBoundaryRoute path={`${match.url}field`} component={Field} />
      <ErrorBoundaryRoute path={`${match.url}field-possible-value`} component={FieldPossibleValue} />
      <ErrorBoundaryRoute path={`${match.url}disposition-submission`} component={DispositionSubmission} />
      <ErrorBoundaryRoute path={`${match.url}disposition-submission-value`} component={DispositionSubmissionValue} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
