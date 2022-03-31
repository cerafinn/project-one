package com.revature.service;

import com.revature.dao.ReimbursementDao;
import com.revature.dto.ResolveReimbursementDTO;
import com.revature.exception.ImageNotFoundException;
import com.revature.model.Reimbursement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReimbursementServiceTest<mockedDTO> {
  ReimbursementDao mockedDao = mock(ReimbursementDao.class);
  ResolveReimbursementDTO mockedDTO = mock(ResolveReimbursementDTO.class);
  ReimbursementService reimbursementService = new ReimbursementService();

  @Test
  public void testGetAllReimbursements() throws SQLException {
  }

  @Test
  public void testGetAllByUser_EmployeePositive() throws SQLException {
  }

  @Test
  public void testGetAllByUser_EmployeeNegative() throws SQLException {
  }

  @Test
  public void testaddNewReimbursement_Success() throws SQLException {}

  @Test
  public void testaddNewReimbursement_Failed() throws SQLException {}

  @Test
  public void testResolveReimbursement_Success() throws SQLException {}

  @Test
  public void testResolveReimbursement_Failed() throws SQLException {}

  @Test
  public void testGetReceipt_Success() throws SQLException, ImageNotFoundException {}

  @Test
  public void testGetReceipt_Failed() throws SQLException, ImageNotFoundException {}

  @Test
  public void testGetByStatus_Success() throws SQLException {}

  @Test
  public void testGetStatus_Failed() throws SQLException {}

  @Test
  public void getReimbByUserAndStatus_Success() throws SQLException {}

  @Test
  public void getReimbByUserAndStatus_Failed() throws SQLException {}
}
