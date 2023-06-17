package com.example.eventsourcing.eventstore.controller;

import com.example.eventsourcing.eventstore.domain.readmodel.Account;
import com.example.eventsourcing.eventstore.domain.readmodel.Payment;
import com.example.eventsourcing.eventstore.repository.AccountRepository;
import com.example.eventsourcing.eventstore.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class UIController {

  private final AccountRepository accountRepository;
  private final PaymentRepository paymentRepository;

  @GetMapping("/create-account")
  public String showCreateAccountForm() {
    return "create-account";
  }

  @GetMapping("/accounts")
  public String showAccountList(Model model) {
    List<Account> accounts = accountRepository.findAll();
    model.addAttribute("accounts", accounts);
    return "account-list";
  }

  @GetMapping("/account/{accountId}/edit")
  public String showUpdateForm(@PathVariable UUID accountId, Model model) {
    Account account = accountRepository.findById(accountId).get();
            //.orElseThrow(() -> new NotFoundException("Account not found"));
    model.addAttribute("account", account);
    return "update-account";
  }

  @GetMapping("/account/paymentView/{accountId}")
  public String accountPayment(@PathVariable UUID accountId, Model model) {
    Account account = accountRepository.findById(accountId).get();
    //.orElseThrow(() -> new NotFoundException("Account not found"));
    model.addAttribute("account", account);
    return "account-payment";
  }

  @GetMapping("/")
  public ModelAndView index() {
    List<Payment> lastFivePayments = paymentRepository.findTop5ByOrderByPaymentDateDesc();
    ModelAndView modelAndView = new ModelAndView("index");
    modelAndView.addObject("payments", lastFivePayments);
    return modelAndView;
  }
}
