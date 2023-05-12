import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

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
import TestResult from './test-result';
import Build from './build';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="client/*" element={<Client />} />
        <Route path="campaign/*" element={<Campaign />} />
        <Route path="lead/*" element={<Lead />} />
        <Route path="attribute-key/*" element={<AttributeKey />} />
        <Route path="attribute-possible-value/*" element={<AttributePossibleValue />} />
        <Route path="attribute/*" element={<Attribute />} />
        <Route path="location/*" element={<Location />} />
        <Route path="telecaller/*" element={<Telecaller />} />
        <Route path="telecaller-in-out/*" element={<TelecallerInOut />} />
        <Route path="telecaller-assignment/*" element={<TelecallerAssignment />} />
        <Route path="lead-association/*" element={<LeadAssociation />} />
        <Route path="lead-assignment/*" element={<LeadAssignment />} />
        <Route path="call/*" element={<Call />} />
        <Route path="lead-upload-file/*" element={<LeadUploadFile />} />
        <Route path="disposition/*" element={<Disposition />} />
        <Route path="field/*" element={<Field />} />
        <Route path="field-possible-value/*" element={<FieldPossibleValue />} />
        <Route path="disposition-submission/*" element={<DispositionSubmission />} />
        <Route path="disposition-submission-value/*" element={<DispositionSubmissionValue />} />
        <Route path="test-result/*" element={<TestResult />} />
        <Route path="build/*" element={<Build />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
