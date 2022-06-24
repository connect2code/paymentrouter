# paymentrouter

Payments is an integral component of any e-commerce or fintech.
With the advent of payments in recent times in digital manner, we have seen different types of payments ecosystem rising up - Payments gateways, UPI, Rupay Network etc. These are easy to integrate and any org can get started with this in a matter of days.
With the onset of different payment players, organizations integrate different payment gateways and shuffle between them to suit their use cases best.
Implement a payments ecosystem which help facilitate a payment for its client, below points should be kept in mind while implementation

## Feature Requirement:
System should support onboarding multiple clients
System should have facility to support different payment methods - UPI, Credit / Debit Card, Netbanking etc
System should support different payment gateways - a router basically
System should have facility to allocate specific percentage between different PGs - say PG1 takes 30% of total traffic while remaining 70% go to PG2
Clients should have an option to opt for specific payment methods. - only UPI, everything except netbanking etc

## Assumptions:
PGs can randomly return success / failure - candidates can create a random function to mock this behaviour.

## Code Expectation:
Everything has to be in memory
Simple and basic function are expected as entry point - no marks for providing fancy restful API or framework implementation
Because this is a machine coding round, heavy focus would be given on code quality, candidate should not focus too much time on algo which compromises with implementation time

## Requirements - below are various functions that should be supported with necessary parameters passed
### addClient() 
    - add a client in ecosystem
### removeClinet() 
    - remove client from ecosystem
### hasClient() 
    - does a client exist in the ecosystem?
### listSupportedPaymodes() 
    - show paymodes support by ecosystem. if a client is passed as parameter, all supported paymodes for the clients should be shows
### addSupportForPaymode() 
    - add paymodes support in the ecosystem. if a client is passed as parameter, add paymode for the clients.
### removePaymode() 
    - remove paymode ( both from ecosystem or client basis parameter)
### showDistribution() 
    - show active distribution percentage of router

## Evaluation criteria:
Working code
Code readability
Implementation of OOPs / OOD principles with proper Separation of concerns
Testability - a TDD approach ( not to be mixed with test cases )
Language proficiency
Test Cases ( bonus points )

## Extension problem:
Can a router dynamically switch the traffic basis success percentage of PG?
Can the router switch based on payment instruments as well instead of just PGs?
[execution time limit] 3 seconds (java)

