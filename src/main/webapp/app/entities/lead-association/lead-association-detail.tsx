import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './lead-association.reducer';

export const LeadAssociationDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const leadAssociationEntity = useAppSelector(state => state.leadAssociation.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="leadAssociationDetailsHeading">
          <Translate contentKey="automatedPerformanceTestingApp.leadAssociation.detail.title">LeadAssociation</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{leadAssociationEntity.id}</dd>
          <dt>
            <span id="assignmentDate">
              <Translate contentKey="automatedPerformanceTestingApp.leadAssociation.assignmentDate">Assignment Date</Translate>
            </span>
          </dt>
          <dd>{leadAssociationEntity.assignmentDate}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="automatedPerformanceTestingApp.leadAssociation.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{leadAssociationEntity.createdBy}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="automatedPerformanceTestingApp.leadAssociation.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            {leadAssociationEntity.createdAt ? (
              <TextFormat value={leadAssociationEntity.createdAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="automatedPerformanceTestingApp.leadAssociation.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{leadAssociationEntity.updatedBy}</dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="automatedPerformanceTestingApp.leadAssociation.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>
            {leadAssociationEntity.updatedAt ? (
              <TextFormat value={leadAssociationEntity.updatedAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="automatedPerformanceTestingApp.leadAssociation.lead">Lead</Translate>
          </dt>
          <dd>{leadAssociationEntity.lead ? leadAssociationEntity.lead.id : ''}</dd>
          <dt>
            <Translate contentKey="automatedPerformanceTestingApp.leadAssociation.campaign">Campaign</Translate>
          </dt>
          <dd>{leadAssociationEntity.campaign ? leadAssociationEntity.campaign.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/lead-association" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/lead-association/${leadAssociationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LeadAssociationDetail;
