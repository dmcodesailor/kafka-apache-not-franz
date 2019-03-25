package com.rvnug.exoplanet.exo;

import com.rvnug.exoplanet.EntityBase;
import java.util.Date;

public class ExoplanetData {

    protected int id;
    public int getId(){
        return this.id;
    }
    public void setId(int id){
        this.id = id;
    }

    private String name;
    private String planet_status;
    private double mass;
    private double mass_error_min;
    private double mass_error_max;
    private double mass_sini;
    private double mass_sini_error_min;
    private double mass_sini_error_max;
    private double radius;
    private double radius_error_min;
    private double radius_error_max;
    private double orbital_period;
    private double orbital_period_error_min;
    private double orbital_period_error_max;
    private double semi_major_axis;
    private double semi_major_axis_error_min;
    private double semi_major_axis_error_max;
    private double eccentricity;
    private double eccentricity_error_min;
    private double eccentricity_error_max;
    private double inclination;
    private double inclination_error_min;
    private double inclination_error_max;
    private double angular_distance;
    private int discovered;
    private Date updated;
    private double omega;
    private double omega_error_min;
    private double omega_error_max;
    private double tperi;
    private double tperi_error_min;
    private double tperi_error_max;
    private double tconj;
    private double tconj_error_min;
    private double tconj_error_max;
    private double tzero_tr;
    private double tzero_tr_error_min;
    private double tzero_tr_error_max;
    private double tzero_tr_sec;
    private double tzero_tr_sec_error_min;
    private double tzero_tr_sec_error_max;
    private double lambda_angle;
    private double lambda_angle_error_min;
    private double lambda_angle_error_max;
    private double impact_parameter;
    private double impact_parameter_error_min;
    private double impact_parameter_error_max;
    private double tzero_vr;
    private double tzero_vr_error_min;
    private double tzero_vr_error_max;
    private double k;
    private double k_error_min;
    private double k_error_max;
    private double temp_calculated;
    private double temp_calculated_error_min;
    private double temp_calculated_error_max;
    private double temp_measured;
    private double hot_point_lon;
    private double geometric_albedo;
    private double geometric_albedo_error_min;
    private double geometric_albedo_error_max;
    private double log_g;
    private String privateation;
    private String detection_type;
    private String mass_detection_type;
    private String radius_detection_type;
    private String alternate_names;
    private String molecules;
    private String star_name;
    private double ra;
    private double dec;
    private double mag_v;
    private double mag_i;
    private double mag_j;
    private double mag_h;
    private double mag_k;
    private double star_distance;
    private double star_distance_error_min;
    private double star_distance_error_max;
    private double star_metallicity;
    private double star_metallicity_error_min;
    private double star_metallicity_error_max;
    private double star_mass;
    private double star_mass_error_min;
    private double star_mass_error_max;
    private double star_radius;
    private double star_radius_error_min;
    private double star_radius_error_max;
    private double star_sp_type;
    private double star_age;
    private double star_age_error_min;
    private double star_age_error_max;
    private double star_teff;
    private double star_teff_error_min;
    private double star_teff_error_max;
    private String star_detected_disc;
    private String star_magnetic_field;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlanet_status() {
        return planet_status;
    }

    public void setPlanet_status(String planet_status) {
        this.planet_status = planet_status;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getMass_error_min() {
        return mass_error_min;
    }

    public void setMass_error_min(double mass_error_min) {
        this.mass_error_min = mass_error_min;
    }

    public double getMass_error_max() {
        return mass_error_max;
    }

    public void setMass_error_max(double mass_error_max) {
        this.mass_error_max = mass_error_max;
    }

    public double getMass_sini() {
        return mass_sini;
    }

    public void setMass_sini(double mass_sini) {
        this.mass_sini = mass_sini;
    }

    public double getMass_sini_error_min() {
        return mass_sini_error_min;
    }

    public void setMass_sini_error_min(double mass_sini_error_min) {
        this.mass_sini_error_min = mass_sini_error_min;
    }

    public double getMass_sini_error_max() {
        return mass_sini_error_max;
    }

    public void setMass_sini_error_max(double mass_sini_error_max) {
        this.mass_sini_error_max = mass_sini_error_max;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getRadius_error_min() {
        return radius_error_min;
    }

    public void setRadius_error_min(double radius_error_min) {
        this.radius_error_min = radius_error_min;
    }

    public double getRadius_error_max() {
        return radius_error_max;
    }

    public void setRadius_error_max(double radius_error_max) {
        this.radius_error_max = radius_error_max;
    }

    public double getOrbital_period() {
        return orbital_period;
    }

    public void setOrbital_period(double orbital_period) {
        this.orbital_period = orbital_period;
    }

    public double getOrbital_period_error_min() {
        return orbital_period_error_min;
    }

    public void setOrbital_period_error_min(double orbital_period_error_min) {
        this.orbital_period_error_min = orbital_period_error_min;
    }

    public double getOrbital_period_error_max() {
        return orbital_period_error_max;
    }

    public void setOrbital_period_error_max(double orbital_period_error_max) {
        this.orbital_period_error_max = orbital_period_error_max;
    }

    public double getSemi_major_axis() {
        return semi_major_axis;
    }

    public void setSemi_major_axis(double semi_major_axis) {
        this.semi_major_axis = semi_major_axis;
    }

    public double getSemi_major_axis_error_min() {
        return semi_major_axis_error_min;
    }

    public void setSemi_major_axis_error_min(double semi_major_axis_error_min) {
        this.semi_major_axis_error_min = semi_major_axis_error_min;
    }

    public double getSemi_major_axis_error_max() {
        return semi_major_axis_error_max;
    }

    public void setSemi_major_axis_error_max(double semi_major_axis_error_max) {
        this.semi_major_axis_error_max = semi_major_axis_error_max;
    }

    public double getEccentricity() {
        return eccentricity;
    }

    public void setEccentricity(double eccentricity) {
        this.eccentricity = eccentricity;
    }

    public double getEccentricity_error_min() {
        return eccentricity_error_min;
    }

    public void setEccentricity_error_min(double eccentricity_error_min) {
        this.eccentricity_error_min = eccentricity_error_min;
    }

    public double getEccentricity_error_max() {
        return eccentricity_error_max;
    }

    public void setEccentricity_error_max(double eccentricity_error_max) {
        this.eccentricity_error_max = eccentricity_error_max;
    }

    public double getInclination() {
        return inclination;
    }

    public void setInclination(double inclination) {
        this.inclination = inclination;
    }

    public double getInclination_error_min() {
        return inclination_error_min;
    }

    public void setInclination_error_min(double inclination_error_min) {
        this.inclination_error_min = inclination_error_min;
    }

    public double getInclination_error_max() {
        return inclination_error_max;
    }

    public void setInclination_error_max(double inclination_error_max) {
        this.inclination_error_max = inclination_error_max;
    }

    public double getAngular_distance() {
        return angular_distance;
    }

    public void setAngular_distance(double angular_distance) {
        this.angular_distance = angular_distance;
    }

    public int getDiscovered() {
        return discovered;
    }

    public void setDiscovered(int discovered) {
        this.discovered = discovered;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public double getOmega() {
        return omega;
    }

    public void setOmega(double omega) {
        this.omega = omega;
    }

    public double getOmega_error_min() {
        return omega_error_min;
    }

    public void setOmega_error_min(double omega_error_min) {
        this.omega_error_min = omega_error_min;
    }

    public double getOmega_error_max() {
        return omega_error_max;
    }

    public void setOmega_error_max(double omega_error_max) {
        this.omega_error_max = omega_error_max;
    }

    public double getTperi() {
        return tperi;
    }

    public void setTperi(double tperi) {
        this.tperi = tperi;
    }

    public double getTperi_error_min() {
        return tperi_error_min;
    }

    public void setTperi_error_min(double tperi_error_min) {
        this.tperi_error_min = tperi_error_min;
    }

    public double getTperi_error_max() {
        return tperi_error_max;
    }

    public void setTperi_error_max(double tperi_error_max) {
        this.tperi_error_max = tperi_error_max;
    }

    public double getTconj() {
        return tconj;
    }

    public void setTconj(double tconj) {
        this.tconj = tconj;
    }

    public double getTconj_error_min() {
        return tconj_error_min;
    }

    public void setTconj_error_min(double tconj_error_min) {
        this.tconj_error_min = tconj_error_min;
    }

    public double getTconj_error_max() {
        return tconj_error_max;
    }

    public void setTconj_error_max(double tconj_error_max) {
        this.tconj_error_max = tconj_error_max;
    }

    public double getTzero_tr() {
        return tzero_tr;
    }

    public void setTzero_tr(double tzero_tr) {
        this.tzero_tr = tzero_tr;
    }

    public double getTzero_tr_error_min() {
        return tzero_tr_error_min;
    }

    public void setTzero_tr_error_min(double tzero_tr_error_min) {
        this.tzero_tr_error_min = tzero_tr_error_min;
    }

    public double getTzero_tr_error_max() {
        return tzero_tr_error_max;
    }

    public void setTzero_tr_error_max(double tzero_tr_error_max) {
        this.tzero_tr_error_max = tzero_tr_error_max;
    }

    public double getTzero_tr_sec() {
        return tzero_tr_sec;
    }

    public void setTzero_tr_sec(double tzero_tr_sec) {
        this.tzero_tr_sec = tzero_tr_sec;
    }

    public double getTzero_tr_sec_error_min() {
        return tzero_tr_sec_error_min;
    }

    public void setTzero_tr_sec_error_min(double tzero_tr_sec_error_min) {
        this.tzero_tr_sec_error_min = tzero_tr_sec_error_min;
    }

    public double getTzero_tr_sec_error_max() {
        return tzero_tr_sec_error_max;
    }

    public void setTzero_tr_sec_error_max(double tzero_tr_sec_error_max) {
        this.tzero_tr_sec_error_max = tzero_tr_sec_error_max;
    }

    public double getLambda_angle() {
        return lambda_angle;
    }

    public void setLambda_angle(double lambda_angle) {
        this.lambda_angle = lambda_angle;
    }

    public double getLambda_angle_error_min() {
        return lambda_angle_error_min;
    }

    public void setLambda_angle_error_min(double lambda_angle_error_min) {
        this.lambda_angle_error_min = lambda_angle_error_min;
    }

    public double getLambda_angle_error_max() {
        return lambda_angle_error_max;
    }

    public void setLambda_angle_error_max(double lambda_angle_error_max) {
        this.lambda_angle_error_max = lambda_angle_error_max;
    }

    public double getImpact_parameter() {
        return impact_parameter;
    }

    public void setImpact_parameter(double impact_parameter) {
        this.impact_parameter = impact_parameter;
    }

    public double getImpact_parameter_error_min() {
        return impact_parameter_error_min;
    }

    public void setImpact_parameter_error_min(double impact_parameter_error_min) {
        this.impact_parameter_error_min = impact_parameter_error_min;
    }

    public double getImpact_parameter_error_max() {
        return impact_parameter_error_max;
    }

    public void setImpact_parameter_error_max(double impact_parameter_error_max) {
        this.impact_parameter_error_max = impact_parameter_error_max;
    }

    public double getTzero_vr() {
        return tzero_vr;
    }

    public void setTzero_vr(double tzero_vr) {
        this.tzero_vr = tzero_vr;
    }

    public double getTzero_vr_error_min() {
        return tzero_vr_error_min;
    }

    public void setTzero_vr_error_min(double tzero_vr_error_min) {
        this.tzero_vr_error_min = tzero_vr_error_min;
    }

    public double getTzero_vr_error_max() {
        return tzero_vr_error_max;
    }

    public void setTzero_vr_error_max(double tzero_vr_error_max) {
        this.tzero_vr_error_max = tzero_vr_error_max;
    }

    public double getK() {
        return k;
    }

    public void setK(double k) {
        this.k = k;
    }

    public double getK_error_min() {
        return k_error_min;
    }

    public void setK_error_min(double k_error_min) {
        this.k_error_min = k_error_min;
    }

    public double getK_error_max() {
        return k_error_max;
    }

    public void setK_error_max(double k_error_max) {
        this.k_error_max = k_error_max;
    }

    public double getTemp_calculated() {
        return temp_calculated;
    }

    public void setTemp_calculated(double temp_calculated) {
        this.temp_calculated = temp_calculated;
    }

    public double getTemp_calculated_error_min() {
        return temp_calculated_error_min;
    }

    public void setTemp_calculated_error_min(double temp_calculated_error_min) {
        this.temp_calculated_error_min = temp_calculated_error_min;
    }

    public double getTemp_calculated_error_max() {
        return temp_calculated_error_max;
    }

    public void setTemp_calculated_error_max(double temp_calculated_error_max) {
        this.temp_calculated_error_max = temp_calculated_error_max;
    }

    public double getTemp_measured() {
        return temp_measured;
    }

    public void setTemp_measured(double temp_measured) {
        this.temp_measured = temp_measured;
    }

    public double getHot_point_lon() {
        return hot_point_lon;
    }

    public void setHot_point_lon(double hot_point_lon) {
        this.hot_point_lon = hot_point_lon;
    }

    public double getGeometric_albedo() {
        return geometric_albedo;
    }

    public void setGeometric_albedo(double geometric_albedo) {
        this.geometric_albedo = geometric_albedo;
    }

    public double getGeometric_albedo_error_min() {
        return geometric_albedo_error_min;
    }

    public void setGeometric_albedo_error_min(double geometric_albedo_error_min) {
        this.geometric_albedo_error_min = geometric_albedo_error_min;
    }

    public double getGeometric_albedo_error_max() {
        return geometric_albedo_error_max;
    }

    public void setGeometric_albedo_error_max(double geometric_albedo_error_max) {
        this.geometric_albedo_error_max = geometric_albedo_error_max;
    }

    public double getLog_g() {
        return log_g;
    }

    public void setLog_g(double log_g) {
        this.log_g = log_g;
    }

    public String getPrivateation() {
        return privateation;
    }

    public void setPrivateation(String privateation) {
        this.privateation = privateation;
    }

    public String getDetection_type() {
        return detection_type;
    }

    public void setDetection_type(String detection_type) {
        this.detection_type = detection_type;
    }

    public String getMass_detection_type() {
        return mass_detection_type;
    }

    public void setMass_detection_type(String mass_detection_type) {
        this.mass_detection_type = mass_detection_type;
    }

    public String getRadius_detection_type() {
        return radius_detection_type;
    }

    public void setRadius_detection_type(String radius_detection_type) {
        this.radius_detection_type = radius_detection_type;
    }

    public String getAlternate_names() {
        return alternate_names;
    }

    public void setAlternate_names(String alternate_names) {
        this.alternate_names = alternate_names;
    }

    public String getMolecules() {
        return molecules;
    }

    public void setMolecules(String molecules) {
        this.molecules = molecules;
    }

    public String getStar_name() {
        return star_name;
    }

    public void setStar_name(String star_name) {
        this.star_name = star_name;
    }

    public double getRa() {
        return ra;
    }

    public void setRa(double ra) {
        this.ra = ra;
    }

    public double getDec() {
        return dec;
    }

    public void setDec(double dec) {
        this.dec = dec;
    }

    public double getMag_v() {
        return mag_v;
    }

    public void setMag_v(double mag_v) {
        this.mag_v = mag_v;
    }

    public double getMag_i() {
        return mag_i;
    }

    public void setMag_i(double mag_i) {
        this.mag_i = mag_i;
    }

    public double getMag_j() {
        return mag_j;
    }

    public void setMag_j(double mag_j) {
        this.mag_j = mag_j;
    }

    public double getMag_h() {
        return mag_h;
    }

    public void setMag_h(double mag_h) {
        this.mag_h = mag_h;
    }

    public double getMag_k() {
        return mag_k;
    }

    public void setMag_k(double mag_k) {
        this.mag_k = mag_k;
    }

    public double getStar_distance() {
        return star_distance;
    }

    public void setStar_distance(double star_distance) {
        this.star_distance = star_distance;
    }

    public double getStar_distance_error_min() {
        return star_distance_error_min;
    }

    public void setStar_distance_error_min(double star_distance_error_min) {
        this.star_distance_error_min = star_distance_error_min;
    }

    public double getStar_distance_error_max() {
        return star_distance_error_max;
    }

    public void setStar_distance_error_max(double star_distance_error_max) {
        this.star_distance_error_max = star_distance_error_max;
    }

    public double getStar_metallicity() {
        return star_metallicity;
    }

    public void setStar_metallicity(double star_metallicity) {
        this.star_metallicity = star_metallicity;
    }

    public double getStar_metallicity_error_min() {
        return star_metallicity_error_min;
    }

    public void setStar_metallicity_error_min(double star_metallicity_error_min) {
        this.star_metallicity_error_min = star_metallicity_error_min;
    }

    public double getStar_metallicity_error_max() {
        return star_metallicity_error_max;
    }

    public void setStar_metallicity_error_max(double star_metallicity_error_max) {
        this.star_metallicity_error_max = star_metallicity_error_max;
    }

    public double getStar_mass() {
        return star_mass;
    }

    public void setStar_mass(double star_mass) {
        this.star_mass = star_mass;
    }

    public double getStar_mass_error_min() {
        return star_mass_error_min;
    }

    public void setStar_mass_error_min(double star_mass_error_min) {
        this.star_mass_error_min = star_mass_error_min;
    }

    public double getStar_mass_error_max() {
        return star_mass_error_max;
    }

    public void setStar_mass_error_max(double star_mass_error_max) {
        this.star_mass_error_max = star_mass_error_max;
    }

    public double getStar_radius() {
        return star_radius;
    }

    public void setStar_radius(double star_radius) {
        this.star_radius = star_radius;
    }

    public double getStar_radius_error_min() {
        return star_radius_error_min;
    }

    public void setStar_radius_error_min(double star_radius_error_min) {
        this.star_radius_error_min = star_radius_error_min;
    }

    public double getStar_radius_error_max() {
        return star_radius_error_max;
    }

    public void setStar_radius_error_max(double star_radius_error_max) {
        this.star_radius_error_max = star_radius_error_max;
    }

    public double getStar_sp_type() {
        return star_sp_type;
    }

    public void setStar_sp_type(double star_sp_type) {
        this.star_sp_type = star_sp_type;
    }

    public double getStar_age() {
        return star_age;
    }

    public void setStar_age(double star_age) {
        this.star_age = star_age;
    }

    public double getStar_age_error_min() {
        return star_age_error_min;
    }

    public void setStar_age_error_min(double star_age_error_min) {
        this.star_age_error_min = star_age_error_min;
    }

    public double getStar_age_error_max() {
        return star_age_error_max;
    }

    public void setStar_age_error_max(double star_age_error_max) {
        this.star_age_error_max = star_age_error_max;
    }

    public double getStar_teff() {
        return star_teff;
    }

    public void setStar_teff(double star_teff) {
        this.star_teff = star_teff;
    }

    public double getStar_teff_error_min() {
        return star_teff_error_min;
    }

    public void setStar_teff_error_min(double star_teff_error_min) {
        this.star_teff_error_min = star_teff_error_min;
    }

    public double getStar_teff_error_max() {
        return star_teff_error_max;
    }

    public void setStar_teff_error_max(double star_teff_error_max) {
        this.star_teff_error_max = star_teff_error_max;
    }

    public String getStar_detected_disc() {
        return star_detected_disc;
    }

    public void setStar_detected_disc(String star_detected_disc) {
        this.star_detected_disc = star_detected_disc;
    }

    public String getStar_magnetic_field() {
        return star_magnetic_field;
    }

    public void setStar_magnetic_field(String star_magnetic_field) {
        this.star_magnetic_field = star_magnetic_field;
    }

    public String getStar_alternate_names() {
        return star_alternate_names;
    }

    public void setStar_alternate_names(String star_alternate_names) {
        this.star_alternate_names = star_alternate_names;
    }

    private String star_alternate_names;

}
