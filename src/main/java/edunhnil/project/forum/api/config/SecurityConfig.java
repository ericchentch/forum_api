package edunhnil.project.forum.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.BeanIds;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import edunhnil.project.forum.api.jwt.JwtAuthenticationFilter;
import edunhnil.project.forum.api.service.userService.UserDetailServiceImpl;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        private UserDetailServiceImpl userDetailService;

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean(BeanIds.AUTHENTICATION_MANAGER)
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
                // Get AuthenticationManager bean
                return super.authenticationManagerBean();
        }

        @Bean
        public JwtAuthenticationFilter jwtAuthenticationFilter() {
                return new JwtAuthenticationFilter();
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
                http
                                .cors().and() // block strange domain
                                .csrf()
                                .disable()
                                .authorizeRequests()
                                .antMatchers("/auth/login", "/auth/register").permitAll()
                                .antMatchers("/auth/logout").hasAnyRole("ADMIN", "USER", "DEV")
                                .antMatchers("/detail/admin/**",
                                                "/account/admin/**", "/post/admin/**", "/comment/admin/**")
                                .hasAnyRole("ADMIN", "DEV")
                                .antMatchers("/account/user/{id}/changePassword", "/account/user/{id}/userName",
                                                "/account/user/updateUser/{id}", "/account/user/deleteUser/{id}",
                                                "/post/user/{id}/getListPost",
                                                "/post/user/{id}/addNewPost", "/comment/user/{id}/addNewComment/**",
                                                "/like/user/{id}/**", "/detail/user/{id}/**")
                                .access("@guard.checkUserId(authentication,#id)")
                                .antMatchers("/post/user/{id}/getPost/{postId}", "/post/user/{id}/updatePost/{postId}",
                                                "/post/user/{id}/deletePost/{postId}",
                                                "/post/user/{id}/changeEnabled/{postId}")
                                .access("@guard.checkAuthorId(authentication,#id,#postId)")
                                .antMatchers("/comment/user/{id}/editComment/{commentId}",
                                                "/comment/user/{id}/deleteComment/{commentId}")
                                .access("@guard.checkCommentId(authentication, #id, #commentId)")
                                .antMatchers("/category/getList", "/detail/public/**", "/post/public/**",
                                                "/comment/public/**",
                                                "/like/public/**", "/detail/public/**")
                                .permitAll()
                                .antMatchers("/swagger-ui/**", "/api-docs/**").permitAll()
                                .anyRequest().authenticated();
                http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        }

        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
                auth.userDetailsService(userDetailService)
                                .passwordEncoder(passwordEncoder());
        }

}
