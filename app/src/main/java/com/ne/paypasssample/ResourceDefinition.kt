package com.ne.paypasssample


import com.google.api.services.walletobjects.model.*
import java.util.*


object ResourceDefinition {

    fun makeLoyaltyClassResource(): LoyaltyClass? { // Define the resource representation of the Class
        // values should be from your DB/services; here we hardcode information
        // below defines an loyalty class. For more properties, check:
        //// https://developers.google.com/pay/passes/reference/v1/loyaltyclass/insert
        //// https://developers.google.com/pay/passes/guides/pass-verticals/loyalty/design
        // There is a client lib to help make the data structure. Newest client is on
        // devsite:
        //// https://developers.google.com/pay/passes/support/libraries#libraries

        val issuerId = "3388000000001876442"
        val loyaltyClass = "LoyaltyClass2"


        val classId = "${issuerId}.${loyaltyClass}"

        return LoyaltyClass() // required
                .setId(classId).setIssuerName("The Home Depot").setProgramName("Pro Xtra 2")
                .setProgramLogo(Image().setSourceUri(
                        ImageUri().setUri("https://cdn-static.findly.com/wp-content/uploads/sites/596/2018/08/HDLogo-univ.png")))
                .setReviewStatus("underReview") // optional. Check design and reference api for more
                .setTextModulesData((object : ArrayList<TextModuleData?>() {
                    init {
                        add(TextModuleData().setHeader("Rewards details").setBody("Welcome to Baconrista rewards.  Enjoy your rewards for being a loyal customer. "
                                + "10 points for ever dollar spent.  Redeem your points for free coffee, bacon and more! "))
                    }
                })).setLinksModuleData((LinksModuleData()).setUris((object : ArrayList<Uri?>() {
                    init {
                        add((Uri()).setDescription("Nearby Locations").setUri("http://maps.google.com/"))
                        add((Uri()).setDescription("Call Customer Service").setUri("tel:6505555555"))
                    }
                }))).setImageModulesData((object : ArrayList<ImageModuleData?>() {
                    init {
                        add((ImageModuleData())
                                .setMainImage((Image()).setSourceUri((ImageUri()).setDescription("Coffee beans")
                                        .setUri("http://farm4.staticflickr.com/3738/12440799783_3dc3c20606_b.jpg"))))
                    }
                })).setMessages((object : ArrayList<Message?>() {
                    init {
                        add((Message()).setHeader("Welcome to Banconrista Rewards!")
                                .setBody("Featuring our new bacon donuts."))
                    }
                })).setRewardsTier("Gold").setRewardsTierLabel("Tier").setLocations((object : ArrayList<LatLongPoint?>() {
                    init {
                        add((LatLongPoint()).setLatitude(37.424015499999996).setLongitude(-122.09259560000001))
                        add((LatLongPoint()).setLatitude(37.7901435).setLongitude(-122.09508869999999))
                        add((LatLongPoint()).setLatitude(37.424354).setLongitude(-122.39026709999997))
                        add((LatLongPoint()).setLatitude(40.7406578).setLongitude(-74.00208940000002))
                    }
                }))
    }
    /******************************
     *
     * Define a Loyalty Object
     *
     * See https://developers.google.com/pay/passes/reference/v1/loyaltyobject
     *
     * @param String classId - The unique identifier for a class
     * @param String objectId - The unique identifier for an object
     * @return LoyaltyObject payload - represents Loyalty object resource
     */
    fun makeLoyaltyObjectResource(): LoyaltyObject { // Define the resource representation of the Class
        // values should be from your DB/services; here we hardcode information
        // below defines an loyalty class. For more properties, check:
        //// https://developers.google.com/pay/passes/reference/v1/loyaltyobject/insert
        //// https://developers.google.com/pay/passes/guides/pass-verticals/loyalty/design
        // There is a client lib to help make the data structure. Newest client is on
        // devsite:
        //// https://developers.google.com/pay/passes/support/libraries#libraries

        val issuerId = "3388000000001876442"
        val loyaltyClass = "LoyaltyClass2"
        val loyaltyObject = "LObject012"

        val classId = "${issuerId}.${loyaltyClass}"
        val objectId = "${issuerId}.${loyaltyObject}"

        return LoyaltyObject() // required
                .setId(objectId).setClassId(classId).setState("active") // optional properties
                .setAccountId("981237654366").setAccountName("Jane Doe")
                .setBarcode(Barcode().setType("qrCode").setValue("1234abc")
                        .setAlternateText("optional alternate text"))
                .setTextModulesData(object : ArrayList<TextModuleData?>() {
                    init {
                        add(TextModuleData().setHeader("Jane\"s Baconrista Rewards")
                                .setBody("Save more at your local Mountain View store Jane. "
                                        + " You get 1 bacon fat latte for every 5 coffees purchased.  "
                                        + "Also just for you, 10% off all pastries in the Mountain View store."))
                    }
                }).setLinksModuleData(LinksModuleData().setUris(object : ArrayList<Uri?>() {
                    init {
                        add(Uri().setDescription("My Baconrista Account").setUri("http://www.google.com"))
                    }
                })).setInfoModuleData(InfoModuleData().setLabelValueRows(object : ArrayList<LabelValueRow?>() {
                    init {
                        add(LabelValueRow().setColumns(object : ArrayList<LabelValue?>() {
                            init {
                                add(LabelValue().setLabel("Next Reward in").setValue("2 coffees"))
                                add(LabelValue().setLabel("Member Since").setValue("01/15/2013"))
                            }
                        }))
                        add(LabelValueRow().setColumns(object : ArrayList<LabelValue?>() {
                            init {
                                add(LabelValue().setLabel("Local Store").setValue("Mountain View"))
                            }
                        }))
                    }
                }).setShowLastUpdateTime(true)).setMessages(object : ArrayList<Message?>() {
                    init {
                        add(Message().setHeader("Jane, welcome to Banconrista Rewards")
                                .setBody("Thanks for joining our program. Show this message to "
                                        + "our barista for your first free coffee on us!"))
                    }
                }).setLoyaltyPoints(LoyaltyPoints().setBalance(LoyaltyPointsBalance().setString("800"))
                        .setLabel("Points"))
                .setLocations(object : ArrayList<LatLongPoint?>() {
                    init {
                        add(LatLongPoint().setLatitude(37.793484).setLongitude(-122.394380))
                    }
                })
    }



}