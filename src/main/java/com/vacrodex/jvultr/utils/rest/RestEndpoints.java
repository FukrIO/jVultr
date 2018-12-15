package com.vacrodex.jvultr.utils.rest;

import lombok.AllArgsConstructor;
import okhttp3.HttpUrl;

/**
 * @author Cameron Wolfe
 */
@AllArgsConstructor
public enum RestEndpoints {
  
  ACCOUNT_INFORMATION("account/info"),
  
  APPLICATION_LIST("app/list"),
  
  AUTHORIZATION_INFORMATION("auth/info"),
  
  BACKUP_LIST("backup/list"),
  
  BAREMETAL_APPLICATION_CHANGE("baremetal/app_change"),
  BAREMETAL_APPLICATION_CHANGE_LIST("baremetal/app_change_list?SUBID=%s"),
  BAREMETAL_BANDWIDTH("baremetal/bandwidth?SUBID=%s"),
  BAREMETAL_CREATE("baremetal/create"),
  BAREMTEAL_DESTROY("baremetal/destroy"),
  BAREMETAL_GET_APPLICATION_INFO("baremetal/get_app_info?SUBID=%s"),
  BAREMETAL_GET_USER_DATA("baremetal/get_user_data?SUBID=%s"),
  BAREMETAL_HALT("baremetal/halt"),
  BAREMETAL_IPV6_ENABLE("baremetal/ipv6_enable"),
  BAREMETAL_LABEL_SET("baremetal/label_set"),
  BAREMETAL_LIST("baremetal/list"),
  BAREMETAL_LIST_IPV4("baremetal/list_ipv4?SUBID=%s"),
  BAREMETAL_LIST_IPV6("baremetal/list_ipv6?SUBID=%s"),
  BAREMETAL_OS_CHANGE("baremetal/os_change"),
  BAREMETAL_OS_CHANGE_LIST("baremetal/os_change_list?SUBID=%s"),
  BAREMETAL_REBOOT("baremetal/reboot"),
  BAREMETAL_REINSTALL("baremetal/reinstall"),
  BAREMETAL_SET_USER_DATA("baremetal/set_user_data"),
  BAREMETAL_TAG_SET("baremetal/tag_set"),
  
  BLOCK_ATTACH("block/attach"),
  BLOCK_CREATE("block/create"),
  BLOCK_DELETE("block/delete"),
  BLOCK_DETACH("block/detach"),
  BLOCK_LABEL_SET("block/label_set"),
  BLOCK_LIST("block/list"),
  BLOCK_RESIZE("block/resize"),
  
  DNS_CREATE_DOMAIN("dns/create_domain"),
  DNS_CREATE_RECORD("dns/create_record"),
  DNS_DELETE_DOMAIN("dns/delete_domain"),
  DNS_DELETE_RECORD("dns/delete_record"),
  DNS_DNSSEC_ENABLE("dns/dnssec_enable"),
  DNS_DNSSEC_INFORMATION("dns/dnssec_info?domain=%s"),
  DNS_LIST("dns/list?domain=%s"),
  DNS_RECORDS("dns/records"),
  DNS_SOA_INFORMATION("dns/soa_info?domain=%s"),
  DNS_SOA_UPDATE("dns/soa_update"),
  DNS_UPDATE_RECORD("dns/update_record"),
  
  FIREWALL_GROUP_CREATE("firewall/group_create"),
  FIREWALL_GROUP_DELETE("firewall/group_delete"),
  FIREWALL_GROUP_LIST("firewall/group_list"),
  FIREWALL_GROUP_SET_DESCRIPTION("firewall/group_set_description"),
  FIREWALL_RULE_CREATE("firewall/rule_create"),
  FIREWALL_RULE_DELETE("firewall/rule_delete"),
  FIREWALL_RULE_LIST("firewall/rule_list?FIREWALLGROUPID=%s&direciton=%s&ip_type=%s"),
  
  ISO_CREATE_FORM_URL("iso/create_from_url"),
  ISO_LIST("iso/list"),
  
  OS_LIST("os/list"),
  
  PLANS_LIST("plans/list?type=%s"),
  PLANS_LIST_BAREMETAL("plans/list_baremetal"),
  PLANS_LIST_VC2("plans/list_vc2"),
  PLANS_LIST_VDC2("plans/list_vdc2"),
  
  REGIONS_AVAILABILITY("regions/availability?DCID=%s"),
  REGIONS_AVAILABILITY_BAREMETAL("regions/availability_baremetal?DCID=%s"),
  REGIONS_AVAILABILITY_VC2("regions/availability_vc2?DCID=%s"),
  REGIONS_AVAILABILITY_VDC2("regions/availability_vdc2?DCID=%s"),
  REGIONS_LIST("regions/list?availability=yes"),
  
  RESERVED_IP_ATTATCH("reservedip/attach"),
  RESERVED_IP_CONVERT("reservedip/convert"),
  RESERVED_IP_CREATE("reservedip/create"),
  RESERVED_IP_DESTROY("reservedip/destroy"),
  RESERVED_IP_DETATCH("reservedip/detatch"),
  RESERVED_IP_LIST("reservedip/list"),
  
  SERVER_APPLICATION_CHANGE("server/app_change?SUBID=%s"),
  SERVER_APPLICATION_LIST("server/app_list"),
  SERVER_BACKUP_DISABLE("server/backup_disable"),
  SERVER_BACKUP_ENABLE("server/backup_enable"),
  SERVER_BACKUP_GET_SCHEDULE("server/backup_get_schedule"),
  SERVER_BACKUP_SET_SCHEDULE("server/backup_set_schedule"),
  SERVER_BANDWIDTH("server/bandwidth?SUBID=%s"),
  SERVER_CREATE("server/create"),
  SERVER_CREATE_IPV4("server/create_ipv4"),
  SERVER_DESTROY("server/destroy"),
  SERVER_DESTROY_IPV4("server/destroy_ipv4"),
  SERVER_FIREWALL_GROUP_SET("server/firewall_group_set"),
  SERVER_GET_APPLICATION_INFO("server/get_app_info?SUBID=%s"),
  SERVER_GET_USER_DATA("server/get_user_data?SUBID=%s"),
  SERVER_HALT("server/halt"),
  SERVER_IPV6_ENABLE("server/ipv6_enable"),
  SERVER_ISO_ATTACH("server/iso_attach"),
  SERVER_ISO_DETACH("server/iso_detach"),
  SERVER_ISO_CREATE("server/iso_create"),
  SERVER_ISO_STATUS("server/iso_status"),
  SERVER_LABEL_SET("server/label_set"),
  SERVER_LIST("server/list"),
  SERVER_LIST_IPV4("server/list_ipv4?SUBID=%s"),
  SERVER_LIST_IPV6("server/list_ipv6?SUBID=%s"),
  SERVER_NEIGHBORS("server/neighbors?SUBID=%s"),
  SERVER_OS_CHANEG("server/os_change"),
  SERVER_OS_LIST("server/os_change_list?SUBID=%s"),
  SERVER_PRIVATE_NETWORK_DISABLE("server/private_network_disable"),
  SERVER_PRIVATE_NETWORK_ENABLE("server/private_network_enable"),
  SERVER_PRIVATE_NETWORKS("server/private_networks?SUBID=%s"),
  SERVER_REBOOT("server/reboot"),
  SERVER_REINSTALL("server/reinstall"),
  SERVER_RESTORE_BACKUP("server/restore_backup"),
  SERVER_RESTORE_SNAPSHOT("server/restore_snapshot"),
  SERVER_REVERSE_DEFAULT_IPV4("server/reverse_default_ipv4"),
  SERVER_REVERSE_DELETE_IPV6("server/reverse_delete_ipv6"),
  SERVER_REVERSE_LIST_IPV6("server/reverse_list_ipv6?SUBID=%s"),
  SERVER_REVERSE_LIST_IPV4("server/reverse_list_ipv4"),
  SERVER_REVERSE_SET_IPV6("server/reverse_set_ipv6"),
  SERVER_SET_USER_DATA("server/set_user_data"),
  SERVER_START("server/start"),
  SERVER_TAG_SET("server/tag_set"),
  SERVER_UPGRADE_PLAN("server/upgrade_plan"),
  SERVER_UPGRADE_PLAN_LIST("server/upgrade_plan_list?SUBID=%s"),
  
  SNAPSHOT_CREATE("snapshot/create"),
  SNAPSHOT_DESTROY("snapshot/destroy"),
  SNAPSHOT_LIST("snapshot/list"),
  
  SSH_KEY_CREATE("sshkey/create"),
  SSH_KEY_DESTROY("sshkey/destroy"),
  SSH_KEY_LIST("sshkey/list"),
  SSH_KEY_UPDATE("sshkey/update"),
  
  STARTUP_SCRIPT_CREATE("startupscript/create"),
  STARTUP_SCRIPT_DESTROY("startupscript/destroy"),
  STARTUP_SCRIPT_LSIT("startupscript/list"),
  STARTUP_SCRIPT_UPDATE("startupscrupt/update"),
  
  USER_CREATE("user/create"),
  USER_DELETE("user/delete"),
  USER_UPDATE("user/update"),
  ;
  
  private final String endpointUrl;
  
  public String getEndpointUrl() {
    return endpointUrl;
  }
  
  public String getFullUrl(String... parameters) {
    //noinspection ConfusingArgumentToVarargsMethod
    return String.format("https://api.vultr.com/v1/" + getEndpointUrl(), parameters);
  }
  
  public HttpUrl getHttpUrl(String... parameters) {
    return HttpUrl.parse(getFullUrl(parameters));
  }
  
}
