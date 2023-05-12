import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './call.reducer';

export const CallDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const callEntity = useAppSelector(state => state.call.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="callDetailsHeading">
          <Translate contentKey="automatedPerformanceTestingApp.call.detail.title">Call</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{callEntity.id}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="automatedPerformanceTestingApp.call.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{callEntity.phone}</dd>
          <dt>
            <span id="callDate">
              <Translate contentKey="automatedPerformanceTestingApp.call.callDate">Call Date</Translate>
            </span>
          </dt>
          <dd>{callEntity.callDate}</dd>
          <dt>
            <span id="followupDate">
              <Translate contentKey="automatedPerformanceTestingApp.call.followupDate">Followup Date</Translate>
            </span>
          </dt>
          <dd>{callEntity.followupDate}</dd>
          <dt>
            <span id="notes">
              <Translate contentKey="automatedPerformanceTestingApp.call.notes">Notes</Translate>
            </span>
          </dt>
          <dd>{callEntity.notes}</dd>
          <dt>
            <span id="recordingUrl">
              <Translate contentKey="automatedPerformanceTestingApp.call.recordingUrl">Recording Url</Translate>
            </span>
          </dt>
          <dd>{callEntity.recordingUrl}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="automatedPerformanceTestingApp.call.status">Status</Translate>
            </span>
          </dt>
          <dd>{callEntity.status}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="automatedPerformanceTestingApp.call.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{callEntity.createdBy}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="automatedPerformanceTestingApp.call.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>{callEntity.createdAt ? <TextFormat value={callEntity.createdAt} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="automatedPerformanceTestingApp.call.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{callEntity.updatedBy}</dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="automatedPerformanceTestingApp.call.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>{callEntity.updatedAt ? <TextFormat value={callEntity.updatedAt} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="automatedPerformanceTestingApp.call.leadAssignment">Lead Assignment</Translate>
          </dt>
          <dd>{callEntity.leadAssignment ? callEntity.leadAssignment.id : ''}</dd>
          <dt>
            <Translate contentKey="automatedPerformanceTestingApp.call.dispositionSubmission">Disposition Submission</Translate>
          </dt>
          <dd>{callEntity.dispositionSubmission ? callEntity.dispositionSubmission.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/call" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/call/${callEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CallDetail;
