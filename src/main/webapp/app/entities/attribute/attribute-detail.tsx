import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './attribute.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AttributeDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const attributeEntity = useAppSelector(state => state.attribute.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="attributeDetailsHeading">
          <Translate contentKey="campaignToolApp.attribute.detail.title">Attribute</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{attributeEntity.id}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="campaignToolApp.attribute.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{attributeEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="campaignToolApp.attribute.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{attributeEntity.createdBy}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="campaignToolApp.attribute.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            {attributeEntity.createdAt ? <TextFormat value={attributeEntity.createdAt} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="campaignToolApp.attribute.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{attributeEntity.updatedBy}</dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="campaignToolApp.attribute.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>
            {attributeEntity.updatedAt ? <TextFormat value={attributeEntity.updatedAt} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="campaignToolApp.attribute.value">Value</Translate>
          </dt>
          <dd>{attributeEntity.value ? attributeEntity.value.id : ''}</dd>
          <dt>
            <Translate contentKey="campaignToolApp.attribute.key">Key</Translate>
          </dt>
          <dd>{attributeEntity.key ? attributeEntity.key.id : ''}</dd>
          <dt>
            <Translate contentKey="campaignToolApp.attribute.lead">Lead</Translate>
          </dt>
          <dd>{attributeEntity.lead ? attributeEntity.lead.id : ''}</dd>
          <dt>
            <Translate contentKey="campaignToolApp.attribute.campaign">Campaign</Translate>
          </dt>
          <dd>{attributeEntity.campaign ? attributeEntity.campaign.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/attribute" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/attribute/${attributeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AttributeDetail;
