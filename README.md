# Grouper Foundation Classes (GFC)

  The GFC framework is a reusable and extensible foundation class library, on which and through
  which all future C&amp;ER components (groupers, editors, ...) will be built, consisting of common framework and common 
  business objects that support our primary grouper deliverables.
  
  
  Furthermore, the GFC project aligns with HIS TLO strategies and addressing C&amp;ER challenges:

  - Ensuring proper operation in SOA environment
  - Modernizing grouper architecture (data handling, code architecture, interfacing, OO design)
  - Optimizing performance (multi-threading, multi-processing capability, efficient architecture)
  - Lowering maintenance cost
  - Producing more reusable code
  - Simplifying the code base
  - Dependency management
  - Inter-component dependency management
  - Providing legacy compatibility
  - Creating a more consistent design between our deliverables


-------------------------


#[[##]]# Software Requirements

  - Java ${java_version} Runtime Environment or higher
  - The latest distribution of Grouper Foundation Classes (GFC)
  
**For Domain generated Java wrappers:** 

  - A Domain generated Java component


#[[##]]# Project setup

  Starting with version **3.4.x**, the GFC project is split up into the following sub projects.
  
  Each sub project is a maven module and is deployed as separate library.


------------------------------


#[[##]]# API

  The API project contains the classes needed to implement a GFC component.
	
  These API frameworks and also the implementations based on them are intended for distribution. The 
  following API modules are created with the *minimal number of dependency requirements* so that implementations 
  using these can be distributed to our customers without having to deal with legal questions from 
  other dependencies.
  		

#[[###]]# Base API

  This module contains the **very minimal classes and interfaces needed to implement a GFC component**.

  If you are starting fresh with implementing a component directly in GFC (no Domain language generated java 
  for example), start here.
  

#[[###]]# Domain API

  This module extends the *Base API* module and adds some 3M Domain language specifics to the API (CTL/ROT paths, 
  resources configuration XML, ...).
	
  If you are wrapping a **3M Domain language generated Java component** with a GFC implementation, then 
  this is the API you should use.


