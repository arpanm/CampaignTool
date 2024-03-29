import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './attribute-possible-value.reducer';

export const AttributePossibleValueDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const attributePossibleValueEntity = useAppSelector(state => state.attributePossibleValue.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="attributePossibleValueDetailsHeading">
          <Translate contentKey="automatedPerformanceTestingApp.attributePossibleValue.detail.title">AttributePossibleValue</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{attributePossibleValueEntity.id}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="automatedPerformanceTestingApp.attributePossibleValue.value">Value</Translate>
            </span>
          </dt>
          <dd>{attributePossibleValueEntity.value}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="automatedPerformanceTestingApp.attributePossibleValue.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{attributePossibleValueEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="automatedPerformanceTestingApp.attributePossibleValue.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{attributePossibleValueEntity.createdBy}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="automatedPerformanceTestingApp.attributePossibleValue.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            {attributePossibleValueEntity.createdAt ? (
              <TextFormat value={attributePossibleValueEntity.createdAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="automatedPerformanceTestingApp.attributePossibleValue.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{attributePossibleValueEntity.updatedBy}</dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="automatedPerformanceTestingApp.attributePossibleValue.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>
            {attributePossibleValueEntity.updatedAt ? (
              <TextFormat value={attributePossibleValueEntity.updatedAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="automatedPerformanceTestingApp.attributePossibleValue.key">Key</Translate>
          </dt>
          <dd>{attributePossibleValueEntity.key ? attributePossibleValueEntity.key.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/attribute-possible-value" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/attribute-possible-value/${attributePossibleValueEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AttributePossibleValueDetail;
