import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './attribute-key.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AttributeKeyDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const attributeKeyEntity = useAppSelector(state => state.attributeKey.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="attributeKeyDetailsHeading">
          <Translate contentKey="campaignToolApp.attributeKey.detail.title">AttributeKey</Translate>
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
              <Translate contentKey="campaignToolApp.attributeKey.key">Key</Translate>
            </span>
          </dt>
          <dd>{attributeKeyEntity.key}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="campaignToolApp.attributeKey.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{attributeKeyEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="campaignToolApp.attributeKey.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{attributeKeyEntity.createdBy}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="campaignToolApp.attributeKey.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            {attributeKeyEntity.createdAt ? (
              <TextFormat value={attributeKeyEntity.createdAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="campaignToolApp.attributeKey.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{attributeKeyEntity.updatedBy}</dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="campaignToolApp.attributeKey.updatedAt">Updated At</Translate>
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
