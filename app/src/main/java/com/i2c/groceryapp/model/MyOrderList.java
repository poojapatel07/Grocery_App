package com.i2c.groceryapp.model;

import java.util.List;

public class MyOrderList {

    /**
     * success : 1
     * data : [{"order_id":34,"order_no":"ORD347530","product_qty":2,"total_amount":"35.00","coupon":null,"discount":"0.00","grand_total":"35.00","shipping_address":"Surat","billing_address":"Surat","status":0,"payment_type":0,"payment_status":0,"order_product_data":[{"order_id":34,"product_id":35,"product_image":{"image":"https://silverfoxstudio.in/fresh_and_fine/public/images/products/15738807265dcf839652e11.jpg","thumb_image":"https://silverfoxstudio.in/fresh_and_fine/public/images/products/thumbnail/15738807265dcf839652e11.jpg"},"name":"NAVRATAN OIL","qty":1,"amount":"120.00","total":"120.00","mrp_price":"100.00","margin":"12.22","product_dp":1,"is_free_product":0,"free_product_id":null,"free_product_image":{"image":"https://silverfoxstudio.in/fresh_and_fine/public/images/products/15739013845dcfd448b2658.jpg","thumb_image":"https://silverfoxstudio.in/fresh_and_fine/public/images/products/thumbnail/15739013845dcfd448b2658.jpg"},"free_product_name":"","total_free_product_qty":0,"created_at":{"date":"2019-11-29 07:36:06.000000","timezone_type":3,"timezone":"UTC"}},{"order_id":34,"product_id":30,"product_image":{"image":"https://silverfoxstudio.in/fresh_and_fine/public/images/products/15739015215dcfd4d18ac96.jpg","thumb_image":"https://silverfoxstudio.in/fresh_and_fine/public/images/products/thumbnail/15739015215dcfd4d18ac96.jpg"},"name":"TATA AGANI","qty":1,"amount":"99.00","total":"99.00","mrp_price":"100.00","margin":"10.11","product_dp":1,"is_free_product":1,"free_product_id":32,"free_product_image":{"image":"https://silverfoxstudio.in/fresh_and_fine/public/images/products/15738802285dcf81a46491e.jpg","thumb_image":"https://silverfoxstudio.in/fresh_and_fine/public/images/products/thumbnail/15738802285dcf81a46491e.jpg"},"free_product_name":"ARIEL","total_free_product_qty":1,"created_at":{"date":"2019-11-29 07:36:06.000000","timezone_type":3,"timezone":"UTC"}}],"order_status_data":[{"status":"0","created_at":{"date":"2019-11-29 07:36:06.000000","timezone_type":3,"timezone":"UTC"}}],"created_at":{"date":"2019-11-29 07:36:06.000000","timezone_type":3,"timezone":"UTC"}},{"order_id":33,"order_no":"ORD336339","product_qty":1,"total_amount":"100.00","coupon":null,"discount":"0.00","grand_total":"100.00","shipping_address":"katargam surat","billing_address":"surat","status":1,"payment_type":0,"payment_status":0,"order_product_data":[{"order_id":33,"product_id":30,"product_image":{"image":"https://silverfoxstudio.in/fresh_and_fine/public/images/products/15739015215dcfd4d18ac96.jpg","thumb_image":"https://silverfoxstudio.in/fresh_and_fine/public/images/products/thumbnail/15739015215dcfd4d18ac96.jpg"},"name":"TATA AGANI","qty":1,"amount":"99.00","total":"99.00","mrp_price":"100.00","margin":"10.11","product_dp":1,"is_free_product":1,"free_product_id":32,"free_product_image":{"image":"https://silverfoxstudio.in/fresh_and_fine/public/images/products/15738802285dcf81a46491e.jpg","thumb_image":"https://silverfoxstudio.in/fresh_and_fine/public/images/products/thumbnail/15738802285dcf81a46491e.jpg"},"free_product_name":"ARIEL","total_free_product_qty":1,"created_at":{"date":"2019-11-27 12:56:38.000000","timezone_type":3,"timezone":"UTC"}}],"order_status_data":[{"status":"0","created_at":{"date":"2019-11-27 12:56:38.000000","timezone_type":3,"timezone":"UTC"}},{"status":"2","created_at":{"date":"2019-11-27 13:01:03.000000","timezone_type":3,"timezone":"UTC"}},{"status":"1","created_at":{"date":"2019-11-27 13:01:14.000000","timezone_type":3,"timezone":"UTC"}}],"created_at":{"date":"2019-11-27 13:01:14.000000","timezone_type":3,"timezone":"UTC"}}]
     * message : Load successfully.
     */

    private String success;
    private String message;
    private List<DataBean> data;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * order_id : 34
         * order_no : ORD347530
         * product_qty : 2
         * total_amount : 35.00
         * coupon : null
         * discount : 0.00
         * grand_total : 35.00
         * shipping_address : Surat
         * billing_address : Surat
         * status : 0
         * payment_type : 0
         * payment_status : 0
         * order_product_data : [{"order_id":34,"product_id":35,"product_image":{"image":"https://silverfoxstudio.in/fresh_and_fine/public/images/products/15738807265dcf839652e11.jpg","thumb_image":"https://silverfoxstudio.in/fresh_and_fine/public/images/products/thumbnail/15738807265dcf839652e11.jpg"},"name":"NAVRATAN OIL","qty":1,"amount":"120.00","total":"120.00","mrp_price":"100.00","margin":"12.22","product_dp":1,"is_free_product":0,"free_product_id":null,"free_product_image":{"image":"https://silverfoxstudio.in/fresh_and_fine/public/images/products/15739013845dcfd448b2658.jpg","thumb_image":"https://silverfoxstudio.in/fresh_and_fine/public/images/products/thumbnail/15739013845dcfd448b2658.jpg"},"free_product_name":"","total_free_product_qty":0,"created_at":{"date":"2019-11-29 07:36:06.000000","timezone_type":3,"timezone":"UTC"}},{"order_id":34,"product_id":30,"product_image":{"image":"https://silverfoxstudio.in/fresh_and_fine/public/images/products/15739015215dcfd4d18ac96.jpg","thumb_image":"https://silverfoxstudio.in/fresh_and_fine/public/images/products/thumbnail/15739015215dcfd4d18ac96.jpg"},"name":"TATA AGANI","qty":1,"amount":"99.00","total":"99.00","mrp_price":"100.00","margin":"10.11","product_dp":1,"is_free_product":1,"free_product_id":32,"free_product_image":{"image":"https://silverfoxstudio.in/fresh_and_fine/public/images/products/15738802285dcf81a46491e.jpg","thumb_image":"https://silverfoxstudio.in/fresh_and_fine/public/images/products/thumbnail/15738802285dcf81a46491e.jpg"},"free_product_name":"ARIEL","total_free_product_qty":1,"created_at":{"date":"2019-11-29 07:36:06.000000","timezone_type":3,"timezone":"UTC"}}]
         * order_status_data : [{"status":"0","created_at":{"date":"2019-11-29 07:36:06.000000","timezone_type":3,"timezone":"UTC"}}]
         * created_at : {"date":"2019-11-29 07:36:06.000000","timezone_type":3,"timezone":"UTC"}
         */

        private int order_id;
        private String order_no;
        private int product_qty;
        private String total_amount;
        private Object coupon;
        private String discount;
        private String grand_total;
        private String shipping_address;
        private String billing_address;
        private int status;
        private int payment_type;
        private int payment_status;
        //        private CreatedAtBean created_at;
        private List<OrderProductDataBean> order_product_data;
        private List<OrderStatusDataBean> order_status_data;

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public int getProduct_qty() {
            return product_qty;
        }

        public void setProduct_qty(int product_qty) {
            this.product_qty = product_qty;
        }

        public String getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(String total_amount) {
            this.total_amount = total_amount;
        }

        public Object getCoupon() {
            return coupon;
        }

        public void setCoupon(Object coupon) {
            this.coupon = coupon;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getGrand_total() {
            return grand_total;
        }

        public void setGrand_total(String grand_total) {
            this.grand_total = grand_total;
        }

        public String getShipping_address() {
            return shipping_address;
        }

        public void setShipping_address(String shipping_address) {
            this.shipping_address = shipping_address;
        }

        public String getBilling_address() {
            return billing_address;
        }

        public void setBilling_address(String billing_address) {
            this.billing_address = billing_address;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getPayment_type() {
            return payment_type;
        }

        public void setPayment_type(int payment_type) {
            this.payment_type = payment_type;
        }

        public int getPayment_status() {
            return payment_status;
        }

        public void setPayment_status(int payment_status) {
            this.payment_status = payment_status;
        }

//        public CreatedAtBean getCreated_at() {
//            return created_at;
//        }

//        public void setCreated_at(CreatedAtBean created_at) {
//            this.created_at = created_at;
//        }

        public List<OrderProductDataBean> getOrder_product_data() {
            return order_product_data;
        }

        public void setOrder_product_data(List<OrderProductDataBean> order_product_data) {
            this.order_product_data = order_product_data;
        }

        public List<OrderStatusDataBean> getOrder_status_data() {
            return order_status_data;
        }

        public void setOrder_status_data(List<OrderStatusDataBean> order_status_data) {
            this.order_status_data = order_status_data;
        }


        public static class OrderProductDataBean {
            /**
             * order_id : 34
             * product_id : 35
             * product_image : {"image":"https://silverfoxstudio.in/fresh_and_fine/public/images/products/15738807265dcf839652e11.jpg","thumb_image":"https://silverfoxstudio.in/fresh_and_fine/public/images/products/thumbnail/15738807265dcf839652e11.jpg"}
             * name : NAVRATAN OIL
             * qty : 1
             * amount : 120.00
             * total : 120.00
             * mrp_price : 100.00
             * margin : 12.22
             * product_dp : 1
             * is_free_product : 0
             * free_product_id : null
             * free_product_image : {"image":"https://silverfoxstudio.in/fresh_and_fine/public/images/products/15739013845dcfd448b2658.jpg","thumb_image":"https://silverfoxstudio.in/fresh_and_fine/public/images/products/thumbnail/15739013845dcfd448b2658.jpg"}
             * free_product_name :
             * total_free_product_qty : 0
             * created_at : {"date":"2019-11-29 07:36:06.000000","timezone_type":3,"timezone":"UTC"}
             */

            private int order_id;
            private int product_id;
            private ProductImageBean product_image;
            private String name;
            private int qty;
            private float amount;
            private String total;
            private String mrp_price;
            private String margin;
            private int product_dp;
            private int is_free_product;
            private Object free_product_id;
            private FreeProductImageBean free_product_image;
            private String free_product_name;
            private int total_free_product_qty;
            private CreatedAtBeanX created_at;

            public int getOrder_id() {
                return order_id;
            }

            public void setOrder_id(int order_id) {
                this.order_id = order_id;
            }

            public int getProduct_id() {
                return product_id;
            }

            public void setProduct_id(int product_id) {
                this.product_id = product_id;
            }

            public ProductImageBean getProduct_image() {
                return product_image;
            }

            public void setProduct_image(ProductImageBean product_image) {
                this.product_image = product_image;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getQty() {
                return qty;
            }

            public void setQty(int qty) {
                this.qty = qty;
            }

            public float getAmount() {
                return amount;
            }

            public void setAmount(float amount) {
                this.amount = amount;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public String getMrp_price() {
                return mrp_price;
            }

            public void setMrp_price(String mrp_price) {
                this.mrp_price = mrp_price;
            }

            public String getMargin() {
                return margin;
            }

            public void setMargin(String margin) {
                this.margin = margin;
            }

            public int getProduct_dp() {
                return product_dp;
            }

            public void setProduct_dp(int product_dp) {
                this.product_dp = product_dp;
            }

            public int getIs_free_product() {
                return is_free_product;
            }

            public void setIs_free_product(int is_free_product) {
                this.is_free_product = is_free_product;
            }

            public Object getFree_product_id() {
                return free_product_id;
            }

            public void setFree_product_id(Object free_product_id) {
                this.free_product_id = free_product_id;
            }

            public FreeProductImageBean getFree_product_image() {
                return free_product_image;
            }

            public void setFree_product_image(FreeProductImageBean free_product_image) {
                this.free_product_image = free_product_image;
            }

            public String getFree_product_name() {
                return free_product_name;
            }

            public void setFree_product_name(String free_product_name) {
                this.free_product_name = free_product_name;
            }

            public int getTotal_free_product_qty() {
                return total_free_product_qty;
            }

            public void setTotal_free_product_qty(int total_free_product_qty) {
                this.total_free_product_qty = total_free_product_qty;
            }

            public CreatedAtBeanX getCreated_at() {
                return created_at;
            }

            public void setCreated_at(CreatedAtBeanX created_at) {
                this.created_at = created_at;
            }

            public static class ProductImageBean {
                /**
                 * image : https://silverfoxstudio.in/fresh_and_fine/public/images/products/15738807265dcf839652e11.jpg
                 * thumb_image : https://silverfoxstudio.in/fresh_and_fine/public/images/products/thumbnail/15738807265dcf839652e11.jpg
                 */

                private String image;
                private String thumb_image;

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                public String getThumb_image() {
                    return thumb_image;
                }

                public void setThumb_image(String thumb_image) {
                    this.thumb_image = thumb_image;
                }
            }

            public static class FreeProductImageBean {
                /**
                 * image : https://silverfoxstudio.in/fresh_and_fine/public/images/products/15739013845dcfd448b2658.jpg
                 * thumb_image : https://silverfoxstudio.in/fresh_and_fine/public/images/products/thumbnail/15739013845dcfd448b2658.jpg
                 */

                private String image;
                private String thumb_image;

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                public String getThumb_image() {
                    return thumb_image;
                }

                public void setThumb_image(String thumb_image) {
                    this.thumb_image = thumb_image;
                }
            }

            public static class CreatedAtBeanX {
                /**
                 * date : 2019-11-29 07:36:06.000000
                 * timezone_type : 3
                 * timezone : UTC
                 */

                private String date;
                private int timezone_type;
                private String timezone;

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public int getTimezone_type() {
                    return timezone_type;
                }

                public void setTimezone_type(int timezone_type) {
                    this.timezone_type = timezone_type;
                }

                public String getTimezone() {
                    return timezone;
                }

                public void setTimezone(String timezone) {
                    this.timezone = timezone;
                }
            }
        }

        public static class OrderStatusDataBean {
            /**
             * status : 0
             * created_at : {"date":"2019-11-29 07:36:06.000000","timezone_type":3,"timezone":"UTC"}
             */

            private String status;
            private CreatedAtBeanXX created_at;

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public CreatedAtBeanXX getCreated_at() {
                return created_at;
            }

            public void setCreated_at(CreatedAtBeanXX created_at) {
                this.created_at = created_at;
            }

            public static class CreatedAtBeanXX {
                /**
                 * date : 2019-11-29 07:36:06.000000
                 * timezone_type : 3
                 * timezone : UTC
                 */

                private String date;
                private int timezone_type;
                private String timezone;

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public int getTimezone_type() {
                    return timezone_type;
                }

                public void setTimezone_type(int timezone_type) {
                    this.timezone_type = timezone_type;
                }

                public String getTimezone() {
                    return timezone;
                }

                public void setTimezone(String timezone) {
                    this.timezone = timezone;
                }
            }
        }
    }


}
