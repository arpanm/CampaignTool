import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './lead-assignment.reducer';

export const LeadAssignmentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const leadAssignmentEntity = useAppSelector(state => state.leadAssignment.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="leadAssignmentDetailsHeading">
          <Translate contentKey="automatedPerformanceTestingApp.leadAssignment.detail.title">LeadAssignment</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{leadAssignmentEntity.id}</dd>
          <dt>
            <span id="assignmentDate">
              <Translate contentKey="automatedPerformanceTestingApp.leadAssignment.assignmentDate">Assignment Date</Translate>
            </span>
          </dt>
          <dd>{leadAssignmentEntity.assignmentDate}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="automatedPerformanceTestingApp.leadAssignment.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{leadAssignmentEntity.createdBy}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="automatedPerformanceTestingApp.leadAssignment.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            {leadAssignmentEntity.createdAt ? (
              <TextFormat value={leadAssignmentEntity.createdAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="automatedPerformanceTestingApp.leadAssignment.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{leadAssignmentEntity.updatedBy}</dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="automatedPerformanceTestingApp.leadAssignment.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>
            {leadAssignmentEntity.updatedAt ? (
              <TextFormat value={leadAssignmentEntity.updatedAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="automatedPerformanceTestingApp.leadAssignment.telecaller">Telecaller</Translate>
          </dt>
          <dd>{leadAssignmentEntity.telecaller ? leadAssignmentEntity.telecaller.id : ''}</dd>
          <dt>
            <Translate contentKey="automatedPerformanceTestingApp.leadAssignment.lead">Lead</Translate>
          </dt>
          <dd>{leadAssignmentEntity.lead ? leadAssignmentEntity.lead.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/lead-assignment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/lead-assignment/${leadAssignmentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LeadAssignmentDetail;
