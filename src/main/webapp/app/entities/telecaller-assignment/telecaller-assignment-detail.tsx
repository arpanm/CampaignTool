import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './telecaller-assignment.reducer';

export const TelecallerAssignmentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const telecallerAssignmentEntity = useAppSelector(state => state.telecallerAssignment.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="telecallerAssignmentDetailsHeading">
          <Translate contentKey="automatedPerformanceTestingApp.telecallerAssignment.detail.title">TelecallerAssignment</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{telecallerAssignmentEntity.id}</dd>
          <dt>
            <span id="assignmentDate">
              <Translate contentKey="automatedPerformanceTestingApp.telecallerAssignment.assignmentDate">Assignment Date</Translate>
            </span>
          </dt>
          <dd>{telecallerAssignmentEntity.assignmentDate}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="automatedPerformanceTestingApp.telecallerAssignment.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{telecallerAssignmentEntity.createdBy}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="automatedPerformanceTestingApp.telecallerAssignment.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            {telecallerAssignmentEntity.createdAt ? (
              <TextFormat value={telecallerAssignmentEntity.createdAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="automatedPerformanceTestingApp.telecallerAssignment.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{telecallerAssignmentEntity.updatedBy}</dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="automatedPerformanceTestingApp.telecallerAssignment.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>
            {telecallerAssignmentEntity.updatedAt ? (
              <TextFormat value={telecallerAssignmentEntity.updatedAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="automatedPerformanceTestingApp.telecallerAssignment.telecaller">Telecaller</Translate>
          </dt>
          <dd>{telecallerAssignmentEntity.telecaller ? telecallerAssignmentEntity.telecaller.id : ''}</dd>
          <dt>
            <Translate contentKey="automatedPerformanceTestingApp.telecallerAssignment.campaign">Campaign</Translate>
          </dt>
          <dd>{telecallerAssignmentEntity.campaign ? telecallerAssignmentEntity.campaign.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/telecaller-assignment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/telecaller-assignment/${telecallerAssignmentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TelecallerAssignmentDetail;
