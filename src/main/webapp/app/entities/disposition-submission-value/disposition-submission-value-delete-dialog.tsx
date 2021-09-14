import React, { useEffect } from 'react';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity } from './disposition-submission-value.reducer';

export const DispositionSubmissionValueDeleteDialog = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const dispositionSubmissionValueEntity = useAppSelector(state => state.dispositionSubmissionValue.entity);
  const updateSuccess = useAppSelector(state => state.dispositionSubmissionValue.updateSuccess);

  const handleClose = () => {
    props.history.push('/disposition-submission-value');
  };

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(dispositionSubmissionValueEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="dispositionSubmissionValueDeleteDialogHeading">
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="campaignToolApp.dispositionSubmissionValue.delete.question">
        <Translate
          contentKey="campaignToolApp.dispositionSubmissionValue.delete.question"
          interpolate={{ id: dispositionSubmissionValueEntity.id }}
        >
          Are you sure you want to delete this DispositionSubmissionValue?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button
          id="jhi-confirm-delete-dispositionSubmissionValue"
          data-cy="entityConfirmDeleteButton"
          color="danger"
          onClick={confirmDelete}
        >
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.delete">Delete</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default DispositionSubmissionValueDeleteDialog;
