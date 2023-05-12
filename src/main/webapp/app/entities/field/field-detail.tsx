import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './field.reducer';

export const FieldDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const fieldEntity = useAppSelector(state => state.field.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="fieldDetailsHeading">
          <Translate contentKey="automatedPerformanceTestingApp.field.detail.title">Field</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{fieldEntity.id}</dd>
          <dt>
            <span id="fieldName">
              <Translate contentKey="automatedPerformanceTestingApp.field.fieldName">Field Name</Translate>
            </span>
          </dt>
          <dd>{fieldEntity.fieldName}</dd>
          <dt>
            <span id="fieldLabel">
              <Translate contentKey="automatedPerformanceTestingApp.field.fieldLabel">Field Label</Translate>
            </span>
          </dt>
          <dd>{fieldEntity.fieldLabel}</dd>
          <dt>
            <span id="fieldType">
              <Translate contentKey="automatedPerformanceTestingApp.field.fieldType">Field Type</Translate>
            </span>
          </dt>
          <dd>{fieldEntity.fieldType}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="automatedPerformanceTestingApp.field.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{fieldEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="automatedPerformanceTestingApp.field.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{fieldEntity.createdBy}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="automatedPerformanceTestingApp.field.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>{fieldEntity.createdAt ? <TextFormat value={fieldEntity.createdAt} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="automatedPerformanceTestingApp.field.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{fieldEntity.updatedBy}</dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="automatedPerformanceTestingApp.field.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>{fieldEntity.updatedAt ? <TextFormat value={fieldEntity.updatedAt} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="automatedPerformanceTestingApp.field.disposition">Disposition</Translate>
          </dt>
          <dd>{fieldEntity.disposition ? fieldEntity.disposition.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/field" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/field/${fieldEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FieldDetail;
