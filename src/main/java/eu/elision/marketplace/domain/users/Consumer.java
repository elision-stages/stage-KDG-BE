package eu.elision.marketplace.domain.users;

import org.apache.tomcat.jni.Address;

import java.util.List;

public class Consumer
{
    private Cart cart;
    private Address mainAddress;
    private List<Address> otherAddresses;
}
