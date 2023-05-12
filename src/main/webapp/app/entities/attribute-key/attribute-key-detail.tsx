import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './attribute-key.reducer';

export const AttributeKeyDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const attributeKeyEntity = useAppSelector(state => state.attributeKey.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="attributeKeyDetailsHeading">
          <Translate contentKey="automatedPerformanceTestingApp.attributeKey.detail.title">AttributeKey</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{attributeKeyEntity.id}</dd>
          <dt>
            <span id="key">
              <Translate contentKey="automatedPerformanceTestingApp.attributeKey.key">Key</Translate>
            </span>
          </dt>
          <dd>{attributeKeyEntity.key}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="automatedPerformanceTestingApp.attributeKey.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{attributeKeyEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="automatedPerformanceTestingApp.attributeKey.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{attributeKeyEntity.createdBy}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="automatedPerformanceTestingApp.attributeKey.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            {attributeKeyEntity.createdAt ? (
              <TextFormat value={attributeKeyEntity.createdAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="automatedPerformanceTestingApp.attributeKey.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{attributeKeyEntity.updatedBy}</dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="automatedPerformanceTestingApp.attributeKey.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>
            {attributeKeyEntity.updatedAt ? (
              <TextFormat value={attributeKeyEntity.updatedAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/attribute-key" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/attribute-key/${attributeKeyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AttributeKeyDetail;
